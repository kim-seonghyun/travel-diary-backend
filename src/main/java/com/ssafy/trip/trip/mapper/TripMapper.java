package com.ssafy.trip.trip.mapper;

import com.ssafy.trip.trip.dto.response.LocationResponseDto;
import com.ssafy.trip.trip.dto.response.TripListResponse;
import com.ssafy.trip.trip.entity.Trip;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TripMapper {

    List<Trip> getAllTripListPagination(@Param("currentPage") Long currentPage,
                                        @Param("showPage") Long showPage);

    List<Trip> getAllCampTripList();

    List<Trip> getCampTripListByLocate(@Param("locationId") Long locationId,
                                       @Param("currentPage") Long currentPage,
                                       @Param("showPage") Long showPage);


    Trip getDetail(Long tripId);

    @Select(value = "select facility_name from trip where id = #{tripId}")
    String findFacilityNameById(Long tripId);

    @Select(value = "select count(*) from trip")
    Long getTotalCount();

    @Select(value = "select count(*) from trip where location_id = #{location_id}")
    Long getTotalCountByLocation(Long locationId);

    @Select(value = "select * from trip")
    List<TripListResponse> searchAllTripList();

    @Select(value = "select * from location")
    List<LocationResponseDto> searchAllLocationList();
}
