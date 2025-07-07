package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.util;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.LaneRequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.TransporterRequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.TransporterRequestDTO.LaneQuote;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperUtilTest {

    /* ———————————————————————————  LANE  ——————————————————————————— */

    @Test
    @DisplayName("toModelLaneList maps each field correctly")
    void testLaneMapping() {
        LaneRequestDTO dto = new LaneRequestDTO();
        dto.setId(1);
        dto.setOrigin("Chennai");
        dto.setDestination("Bangalore");


        List<Lane> result = MapperUtil.toModelLaneList(List.of(dto));

        assertEquals(1, result.size());
        Lane lane = result.getFirst();
        assertEquals(1, lane.getId());
        assertEquals("Chennai", lane.getOrigin());
        assertEquals("Bangalore", lane.getDestination());
    }

    @Test
    @DisplayName("toModelLaneList returns empty list for empty request")
    void testEmptyLaneList() {
        assertTrue(MapperUtil.toModelLaneList(List.of()).isEmpty());
    }

    /* ———————————————————————————  TRANSPORTER  ——————————————————————————— */

    @Test
    @DisplayName("toModelTransporterList maps all fields and nested lane quotes")
    void testTransporterMapping() {
        LaneQuote quote1 = new LaneQuote();
        quote1.setLaneId(101);
        quote1.setQuote(2500);

        LaneQuote quote2 = new LaneQuote();
        quote2.setLaneId(102);
        quote2.setQuote(3000);

        TransporterRequestDTO transporterDto = new TransporterRequestDTO();
        transporterDto.setId(1);
        transporterDto.setName("FastExpress");
        transporterDto.setLaneQuotes(List.of(quote1, quote2));
        List<Transporter> transporters = MapperUtil.toModelTransporterList(List.of(transporterDto));

        assertEquals(1, transporters.size());

        Transporter transporter = transporters.getFirst();
        assertEquals(1, transporter.getId());
        assertEquals("FastExpress", transporter.getName());
        assertEquals(2, transporter.getLaneQuotes().size());

        Transporter.LaneQuote q1 = transporter.getLaneQuotes().getFirst();
        assertEquals(101, q1.getLaneId());
        assertEquals(2500, q1.getQuote());

        Transporter.LaneQuote q2 = transporter.getLaneQuotes().get(1);
        assertEquals(102, q2.getLaneId());
        assertEquals(3000, q2.getQuote());
    }

    @Test
    @DisplayName("toModelTransporterList returns empty list for empty input")
    void testEmptyTransporterList() {
        List<Transporter> result = MapperUtil.toModelTransporterList(List.of());
        assertTrue(result.isEmpty());
    }
}
