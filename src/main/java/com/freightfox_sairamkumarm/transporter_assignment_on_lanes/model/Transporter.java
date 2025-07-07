package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transporter {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LaneQuote{
        private int laneId;
        private int quote;
    }

    private int id;
    private String name;
    private List<LaneQuote> laneQuotes;
}
