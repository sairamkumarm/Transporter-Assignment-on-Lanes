package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private String message;
    private String status;
    private T data;

}
