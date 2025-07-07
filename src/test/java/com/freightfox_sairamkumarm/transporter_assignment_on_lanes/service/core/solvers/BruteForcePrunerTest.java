package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core.solvers;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.Result;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter.LaneQuote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BruteForcePrunerTest {

    private BruteForcePruner solver;

    @BeforeEach
    void setUp() {
        solver = new BruteForcePruner();
    }

    @Test
    @DisplayName("should find optimal 2-transporter combo with full lane coverage")
    void testBasicCase() {
        List<Lane> lanes = List.of(buildLane(1), buildLane(2), buildLane(3));
        List<Transporter> transporters = List.of(
                buildTransporter(1, new int[][]{{1, 100}, {2, 200}}),
                buildTransporter(2, new int[][]{{3, 150}}),
                buildTransporter(3, new int[][]{{1, 90}, {2, 210}, {3, 160}})
        );

        Result result = solver.solve(lanes, transporters, 2);

        assertNotNull(result);
        assertEquals(2, result.getSelectedTransporters().size());
        assertEquals(3, result.getAssignments().size());
        assertEquals(450, result.getTotalCost());
    }


    @Test
    @DisplayName("should return null if no transporter combo can cover all lanes")
    void testImpossibleToCover() {
        List<Lane> lanes = List.of(buildLane(1), buildLane(2), buildLane(3));
        List<Transporter> transporters = List.of(
                buildTransporter(1, new int[][]{{1, 100}}),
                buildTransporter(2, new int[][]{{2, 200}})
        );

        Result result = solver.solve(lanes, transporters, 3);

        assertNull(result);
    }

    @Test
    @DisplayName("should prefer lowest-cost combo among valid options")
    void testMultipleValidCombosPicksMinCost() {
        List<Lane> lanes = List.of(buildLane(1), buildLane(2));
        List<Transporter> transporters = List.of(
                buildTransporter(1, new int[][]{{1, 100}, {2, 200}}),
                buildTransporter(2, new int[][]{{1, 50}}),
                buildTransporter(3, new int[][]{{2, 100}})
        );

        Result result = solver.solve(lanes, transporters, 3);

        assertNotNull(result);
        assertTrue(result.getSelectedTransporters().containsAll(List.of(2, 3)));
        assertEquals(150, result.getTotalCost());
    }

    @Test
    @DisplayName("should allow exact use of max transporters if needed")
    void testUpToMaxTransportersAllowed() {
        List<Lane> lanes = List.of(buildLane(1), buildLane(2), buildLane(3));
        List<Transporter> transporters = List.of(
                buildTransporter(1, new int[][]{{1, 100}}),
                buildTransporter(2, new int[][]{{2, 100}}),
                buildTransporter(3, new int[][]{{3, 100}})
        );

        Result result = solver.solve(lanes, transporters, 3);

        assertNotNull(result);
        assertEquals(3, result.getSelectedTransporters().size());
        assertEquals(300, result.getTotalCost());
    }

    @Test
    @DisplayName("should use fewer than max transporters if optimal")
    void testUsesFewerThanMaxTransporters() {
        List<Lane> lanes = List.of(buildLane(1), buildLane(2));
        List<Transporter> transporters = List.of(
                buildTransporter(1, new int[][]{{1, 50}, {2, 50}}),
                buildTransporter(2, new int[][]{{1, 100}}),
                buildTransporter(3, new int[][]{{2, 100}})
        );

        Result result = solver.solve(lanes, transporters, 3);

        assertNotNull(result);
        assertEquals(1, result.getSelectedTransporters().size());
        assertEquals(100, result.getTotalCost());
    }

    // ——————————————————————————————————————————————
    // Helpers
    // ——————————————————————————————————————————————

    private Lane buildLane(int id) {
        Lane l = new Lane();
        l.setId(id);
        l.setOrigin("O" + id);
        l.setDestination("D" + id);
        return l;
    }

    private Transporter buildTransporter(int id, int[][] quotes) {
        Transporter t = new Transporter();
        t.setId(id);
        t.setName("T" + id);

        List<LaneQuote> qList = new ArrayList<>();
        for (int[] q : quotes) {
            LaneQuote laneQuote = new LaneQuote();
            laneQuote.setLaneId(q[0]);
            laneQuote.setQuote(q[1]);
            qList.add(laneQuote);
        }

        t.setLaneQuotes(qList);
        return t;
    }
}