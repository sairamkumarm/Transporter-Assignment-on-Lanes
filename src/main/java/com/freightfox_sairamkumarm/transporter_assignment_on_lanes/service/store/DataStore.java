package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.store;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataStore {
    private final Map<Integer, Lane> laneStore = new ConcurrentHashMap<>();
    private final Map<Integer, Transporter> transporterStore = new ConcurrentHashMap<>();

    public void saveLane(Lane l){
        laneStore.put(l.getId(), l);
    }

    public void saveAllLanes(List<Lane> Lanes){
        Lanes.forEach(o -> laneStore.put(o.getId(),o));
    }

    public void saveTransporter(Transporter v){
        transporterStore.put(v.getId(), v);
    }
    public void saveAllTransporters(List<Transporter> Transporters) {
        Transporters.forEach(v -> transporterStore.put(v.getId(), v));
    }

    public List<Lane> getAllLanes() {
        return new ArrayList<>(laneStore.values());
    }

    public List<Transporter> getAllTransporters() {
        return new ArrayList<>(transporterStore.values());
    }

    public void clear() {
        laneStore.clear();
        transporterStore.clear();
    }
}
