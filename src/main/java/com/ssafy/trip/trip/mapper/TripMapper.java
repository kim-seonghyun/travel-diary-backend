package com.ssafy.trip.trip.mapper;

import com.ssafy.trip.trip.entity.Trip;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TripMapper {
    List<Trip> getAllCampTripList();

    List<Trip> getCampTripListByLocate(Long locateId);

    Trip getDetail(Long tripId);

    @Select(value = "select facility_name from trip where id = #{tripId}")
    String findFacilityNameById(Long tripId);
}
