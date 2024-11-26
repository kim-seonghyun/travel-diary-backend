package com.ssafy.trip.post.mapper;

import com.ssafy.trip.post.dto.request.TravelDiaryGraphRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TravelDiaryGraphMapper {
    @Update("update travel_diary_graph set city = city + #{city}, sea = sea + #{sea}, festival = festival + #{festival}, valley = valley + #{valley}, mountain = mountain + #{mountain} where travel_diary_id = #{travelDiaryId}")
    void updateTravelDiaryGraph(TravelDiaryGraphRequestDto travelDiaryGraphRequestDto);
}
