package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.Result;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;

import java.util.List;

public interface SolverStrategy {
    public Result solve(List<Lane> lanes, List<Transporter> transporters, int maxTransporters);
}
