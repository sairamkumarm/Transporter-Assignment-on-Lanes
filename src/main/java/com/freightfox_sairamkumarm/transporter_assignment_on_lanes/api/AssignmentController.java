package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.api;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.RequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.ResponseDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core.InputService;
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

    private final InputService inputService;
    private final DataStore dataStore;

    public AssignmentController(InputService inputService, DataStore dataStore) {
        this.inputService = inputService;
        this.dataStore = dataStore;
    }

    @PostMapping("/input")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> takeLanes(@Valid @RequestBody RequestDTO requestDTO){
        inputService.takeInput(requestDTO);
        return ResponseEntity.ok(new ResponseDTO<>("Lanes & Transporters added successfully", "success", new HashMap<>(Map.of("lanes",dataStore.getAllLanes(),"transporters",dataStore.getAllTransporters()))));
    }


}
