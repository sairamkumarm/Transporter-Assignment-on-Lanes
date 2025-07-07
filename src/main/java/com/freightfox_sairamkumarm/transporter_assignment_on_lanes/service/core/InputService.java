package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.core;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.RequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.store.DataStore;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InputService {

    private final DataStore dataStore;

    public InputService(DataStore dataStore) {
        this.dataStore = dataStore;
    }


    public void takeInput(RequestDTO requestDTO){
        try {
            List<Lane> lanes = MapperUtil.toModelLaneList((requestDTO.getLanes()));
            dataStore.saveAllLanes(lanes);
            List<Transporter> transporters = MapperUtil.toModelTransporterList(requestDTO.getTransporters());
            dataStore.saveAllTransporters(transporters);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
