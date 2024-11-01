package com.ssafy.trip.travelgraph.mapper;

import com.ssafy.trip.travelgraph.entity.TravelGraph;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelGraphMapper {
    TravelGraph findTravelGraphByUserId(Long id);

    void generateTravelGraph(Long id);
}
