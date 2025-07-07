package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolverInput {
    @NotNull
    private Integer maxTransporters;
}
