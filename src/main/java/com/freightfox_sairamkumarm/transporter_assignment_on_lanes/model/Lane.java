package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Lane {
    private int id;
    private String origin;
    private String destination;
}
