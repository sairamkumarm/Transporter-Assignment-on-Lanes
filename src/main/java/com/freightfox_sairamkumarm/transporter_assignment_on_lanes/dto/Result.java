package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {


    private int totalCost;
    private List<Assignment> assignments;
    private List<Integer> selectedTransporters;



    public void print() {
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Selected Transporters: " + selectedTransporters);
        for (Assignment a : assignments) {
            System.out.println(a);
        }
    }
}
