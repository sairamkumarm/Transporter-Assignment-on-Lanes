package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core.solvers;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.Assignment;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.Result;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core.SolverStrategy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("bfp")
public class BruteForcePruner implements SolverStrategy {
    @Override
    public Result solve(List<Lane> lanes, List<Transporter> transporters, int maxTransporters) {
//        System.out.println("solving");
        Set<Integer> targetSet = lanes.stream().map(l -> l.getId()).collect(Collectors.toSet());
        List<List<Transporter>> combos = findCoveringCombinations(transporters, targetSet, maxTransporters);

        if (combos.isEmpty()) return null;

        // Pick the minimum cost one
        List<Transporter> bestCombo = null;
        int minCost = Integer.MAX_VALUE;
        List<Assignment> bestAssignments = null;

        for (List<Transporter> combo : combos) {
            int total = 0;
            Map<Integer, Assignment> laneAssignments = new HashMap<>();

            for (Transporter t : combo) {
                for (Transporter.LaneQuote q : t.getLaneQuotes()) {
                    if (!targetSet.contains(q.getLaneId())) continue;
                    if (!laneAssignments.containsKey(q.getLaneId()) || q.getQuote() < getQuote(transporters, laneAssignments.get(q.getLaneId()))) {
                        laneAssignments.put(q.getLaneId(), new Assignment(q.getLaneId(), t.getId()));
                    }
                }
            }
            for (Assignment a : laneAssignments.values()) {
                total += getQuote(transporters, a);
            }

            if (laneAssignments.size() == targetSet.size() && total < minCost) {
                minCost = total;
                bestCombo = combo;
                bestAssignments = new ArrayList<>(laneAssignments.values());
            }
        }

        if (bestCombo == null) return null;

        Set<Integer> selected = bestAssignments.stream()
                .map(Assignment::getTransporterId).collect(Collectors.toSet());
//        System.out.println(bestAssignments);
        return new Result(minCost, bestAssignments, new ArrayList<>(selected));
    }
    private int getQuote(List<Transporter> all, Assignment a) {
        for (Transporter t : all) {
            if (t.getId() == a.getTransporterId()) {
                for (Transporter.LaneQuote q : t.getLaneQuotes()) {
                    if (q.getLaneId() == a.getLaneId()) {
                        return q.getQuote();
                    }
                }
            }
        }
        // If quote not found, something's wrong — fail fast
        throw new IllegalStateException("Missing quote for transporter " + a.getTransporterId() + " on lane " + a.getLaneId());
    }
    public List<List<Transporter>> findCoveringCombinations(
            List<Transporter> transporters,
            Set<Integer> targetSet,
            int maxTransporters
    ) {
        List<List<Transporter>> res = new ArrayList<>();
        backtrack(transporters, targetSet, 0, new ArrayList<>(), new HashSet<>(), targetSet.size(), res, maxTransporters);
        return res;
    }

    private void backtrack(
            List<Transporter> tps,
            Set<Integer> target,
            int idx,
            List<Transporter> combo,
            Set<Integer> union,
            int remaining,
            List<List<Transporter>> res,
            int max
    ) {
        if (combo.size() > max) return;

//        if (combo.size() == max) {
//            if (remaining == 0) res.add(new ArrayList<>(combo));
//            return;
//        }

        if (remaining == 0) {
            res.add(new ArrayList<>(combo));
//            return;
        };

        for (int i = idx; i < tps.size(); i++) {
            Transporter tp = tps.get(i);

            Set<Integer> next = new HashSet<>(union);
            int delta = 0;
            for (Transporter.LaneQuote q : tp.getLaneQuotes()) {
                if (target.contains(q.getLaneId()) && next.add(q.getLaneId()))
                    delta++;
            }

            combo.add(tp);
            backtrack(tps, target, i + 1, combo, next, remaining - delta, res, max);
            combo.remove(combo.size() - 1);
        }
    }

}
