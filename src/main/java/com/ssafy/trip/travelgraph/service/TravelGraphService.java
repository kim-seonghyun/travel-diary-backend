package com.ssafy.trip.travelgraph.service;

import com.ssafy.trip.travelgraph.dto.response.TravelGraphResponse;
import com.ssafy.trip.travelgraph.entity.TravelGraph;
import com.ssafy.trip.travelgraph.mapper.TravelGraphMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TravelGraphService {
    private TravelGraphMapper travelGraphMapper;

    public TravelGraphService(TravelGraphMapper travelGraphMapper) {
        this.travelGraphMapper = travelGraphMapper;
    }

    public TravelGraphResponse getTravelGraph(Long userId) {
        //validation
        TravelGraph travelGraphEntity = travelGraphMapper.findTravelGraphByUserId(userId);

        return TravelGraphResponse.builder()
                .city(travelGraphEntity.getCity())
                .sea(travelGraphEntity.getSea())
                .festival(travelGraphEntity.getFestival())
                .valley(travelGraphEntity.getValley())
                .mountain(travelGraphEntity.getMountain())
                .build();

    }

    public void calculateTravelDegree(Map<String, Object> map) {
        // validation
        travelGraphMapper.calculateTravelDegree(map);
    }

    public void generateGraph(Long userId) {
        //validation
        travelGraphMapper.generateTravelGraph(userId);
    }

}
