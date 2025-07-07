package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import lombok.Data;

@Data
public class Assignment {
    int laneId;
    int transporterId;

    public Assignment(int laneId, int transporterId) {
        this.laneId = laneId;
        this.transporterId = transporterId;
    }

    public String toString() {
        return "{ laneId: " + laneId + ", transporterId: " + transporterId + " }";
    }
}