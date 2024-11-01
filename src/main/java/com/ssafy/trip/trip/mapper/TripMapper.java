package com.ssafy.trip.trip.mapper;

import com.ssafy.trip.trip.entity.Trip;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripMapper {
    List<Trip> getAllCampTripList();

    List<Trip> getCampTripListByLocate(Long locateId);

    Trip getDetail(Long tripId);
}
