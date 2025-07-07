package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.api;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.RequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.ResponseDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.Result;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.SolverInput;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core.AssignmentService;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.store.DataStore;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/transporters")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final DataStore dataStore;

    public AssignmentController(AssignmentService assignmentService, DataStore dataStore) {
        this.assignmentService = assignmentService;
        this.dataStore = dataStore;
    }

    @PostMapping("/input")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> takeLanes(@Valid @RequestBody RequestDTO requestDTO){
        assignmentService.takeInput(requestDTO);
        return ResponseEntity.ok(new ResponseDTO<>("Lanes & Transporters added successfully", "success", new HashMap<>(Map.of("lanes",dataStore.getAllLanes(),"transporters",dataStore.getAllTransporters()))));
    }

    @PostMapping("/assignment")
    public ResponseEntity<ResponseDTO<Result>> assignLanes(@Valid @RequestBody SolverInput solverInput){
        return ResponseEntity.ok(new ResponseDTO<>("Optimised Assignment found", "success", assignmentService.solve(solverInput.getMaxTransporters())));
    }
}
