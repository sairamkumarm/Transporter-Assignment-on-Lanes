package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.RequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.Result;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core.solvers.BruteForcePruner;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.store.DataStore;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.util.MapperUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final DataStore dataStore;
    private final SolverStrategy solverStrategy;

    public AssignmentService(DataStore dataStore, @Qualifier("bfp") SolverStrategy solverStrategy) {
        this.dataStore = dataStore;
        this.solverStrategy = solverStrategy;
    }


    public void takeInput(RequestDTO requestDTO){
        try {
            List<Lane> lanes = MapperUtil.toModelLaneList((requestDTO.getLanes()));
            dataStore.saveAllLanes(lanes);
            List<Transporter> transporters = MapperUtil.toModelTransporterList(requestDTO.getTransporters());
            dataStore.saveAllTransporters(transporters);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Result solve(int maxTransporters){
        try {
            return solverStrategy.solve(dataStore.getAllLanes(), dataStore.getAllTransporters(), maxTransporters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
