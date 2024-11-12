package com.ssafy.trip.travelgraph.mapper;

import com.ssafy.trip.travelgraph.entity.TravelGraph;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface TravelGraphMapper {
    TravelGraph findTravelGraphByUserId(Long id);

    void calculateTravelDegree(Map<String, Object> map);

    void generateTravelGraph(Long id);
}
