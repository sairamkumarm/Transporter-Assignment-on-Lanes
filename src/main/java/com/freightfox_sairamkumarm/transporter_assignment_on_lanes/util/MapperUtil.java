package com.freightfox_sairamkumarm.transporter_assignment_on_lanes.util;

import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.LaneRequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.dto.TransporterRequestDTO;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Lane;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter;
import com.freightfox_sairamkumarm.transporter_assignment_on_lanes.model.Transporter.LaneQuote;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {

    public static List<Lane> toModelLaneList(List<LaneRequestDTO> laneListRequests) {
        return laneListRequests.stream().map(dto -> {
            Lane lane = new Lane();
            lane.setId(dto.getId());
            lane.setOrigin(dto.getOrigin());
            lane.setDestination(dto.getDestination());
            return lane;
        }).collect(Collectors.toList());
    }

    public static List<Transporter> toModelTransporterList(List<TransporterRequestDTO> transporterRequests) {
        return transporterRequests.stream().map(req -> {
            Transporter transporter = new Transporter();
            transporter.setId(req.getId());
            transporter.setName(req.getName());

            List<LaneQuote> laneQuotes = req.getLaneQuotes().stream().map(lq -> {
                LaneQuote quote = new LaneQuote();
                quote.setLaneId(lq.getLaneId());
                quote.setQuote(lq.getQuote());
                return quote;
            }).collect(Collectors.toList());

            transporter.setLaneQuotes(laneQuotes);
            return transporter;
        }).collect(Collectors.toList());
    }

}
