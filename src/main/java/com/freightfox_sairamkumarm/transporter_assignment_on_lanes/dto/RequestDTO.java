package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {
    @NotEmpty
    private List<@Valid LaneRequestDTO> lanes;
    @NotEmpty
    private List<@Valid TransporterRequestDTO> transporters;
}
