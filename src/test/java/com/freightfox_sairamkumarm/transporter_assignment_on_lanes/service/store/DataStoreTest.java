package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.service.store;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter.LaneQuote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataStoreTest {

    private DataStore dataStore;

    @BeforeEach
    void setUp() {
        dataStore = new DataStore();
    }

    @Test
    @DisplayName("saveLane and getAllLanes should store a single lane")
    void testSaveSingleLane() {
        Lane lane = buildLane(1, "Chennai", "Bangalore");
        dataStore.saveLane(lane);

        List<Lane> all = dataStore.getAllLanes();
        assertEquals(1, all.size());
        assertEquals(1, all.getFirst().getId());
        assertEquals("Chennai", all.getFirst().getOrigin());
        assertEquals("Bangalore", all.getFirst().getDestination());
    }

    @Test
    @DisplayName("saveAllLanes should store multiple lanes")
    void testSaveAllLanes() {
        Lane l1 = buildLane(1, "Chennai", "Bangalore");
        Lane l2 = buildLane(2, "Delhi", "Mumbai");

        dataStore.saveAllLanes(List.of(l1, l2));

        List<Lane> all = dataStore.getAllLanes();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(l -> l.getId() == 1));
        assertTrue(all.stream().anyMatch(l -> l.getId() == 2));
    }

    @Test
    @DisplayName("saveTransporter and getAllTransporters should store a single transporter")
    void testSaveSingleTransporter() {
        Transporter t = buildTransporter(1, "RapidMoves");
        dataStore.saveTransporter(t);

        List<Transporter> all = dataStore.getAllTransporters();
        assertEquals(1, all.size());
        assertEquals(1, all.getFirst().getId());
        assertEquals("RapidMoves", all.getFirst().getName());
        assertEquals(1, all.getFirst().getLaneQuotes().size());
    }

    @Test
    @DisplayName("saveAllTransporters should store multiple transporters")
    void testSaveAllTransporters() {
        Transporter t1 = buildTransporter(1, "RapidMoves");
        Transporter t2 = buildTransporter(2, "FastTrack");

        dataStore.saveAllTransporters(List.of(t1, t2));

        List<Transporter> all = dataStore.getAllTransporters();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(t -> t.getId() == 1));
        assertTrue(all.stream().anyMatch(t -> t.getId() == 2));
    }

    @Test
    @DisplayName("clear should wipe both stores")
    void testClear() {
        dataStore.saveLane(buildLane(1, "Chennai", "Bangalore"));
        dataStore.saveTransporter(buildTransporter(1, "RapidMoves"));

        dataStore.clear();

        assertTrue(dataStore.getAllLanes().isEmpty());
        assertTrue(dataStore.getAllTransporters().isEmpty());
    }

    // ——————————————————————————————————————————————————
    // Helpers
    // ——————————————————————————————————————————————————

    private Lane buildLane(int id, String origin, String destination) {
        Lane lane = new Lane();
        lane.setId(id);
        lane.setOrigin(origin);
        lane.setDestination(destination);
        return lane;
    }

    private Transporter buildTransporter(int id, String name) {
        Transporter t = new Transporter();
        t.setId(id);
        t.setName(name);

        LaneQuote quote = new LaneQuote();
        quote.setLaneId(100);
        quote.setQuote(2500);

        t.setLaneQuotes(List.of(quote));
        return t;
    }
}
