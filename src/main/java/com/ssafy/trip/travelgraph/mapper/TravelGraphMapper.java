package com.ssafy.trip.travelgraph.mapper;

import com.ssafy.trip.post.dto.request.TagWeights;
import com.ssafy.trip.travelgraph.entity.TravelGraph;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TravelGraphMapper {
    TravelGraph findTravelGraphByUserId(Long id);

    void calculateTravelDegree(Map<String, Object> map);

    void generateTravelGraph(Long id);

    @Update("update travel_graph set city = city + #{city}, sea = sea + #{sea}, festival = festival + #{festival}, valley = valley + #{valley}, mountain = mountain + #{mountain} where user_id = #{userId}")
    void updateTravelGraph(TagWeights tagWeights);
}
