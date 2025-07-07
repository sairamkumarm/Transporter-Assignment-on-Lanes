package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TransporterRequestDTO {

    @Data
    public static class LaneQuote{
        @NotNull
        private Integer laneId;

        @NotNull
        @Min(1)
        private Integer quote;
    }

    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    @NotEmpty
    private List<@Valid LaneQuote> laneQuotes;
}
