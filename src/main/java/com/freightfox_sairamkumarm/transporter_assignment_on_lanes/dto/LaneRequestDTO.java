package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LaneRequestDTO {

    @NotNull
    private Integer id;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;
}
