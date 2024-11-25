package com.ssafy.trip.trip.service;

import com.ssafy.trip.trip.dto.response.LocationResponseDto;
import com.ssafy.trip.trip.dto.response.TripDetailResponse;
import com.ssafy.trip.trip.dto.response.TripListResponse;
import com.ssafy.trip.trip.entity.Trip;
import com.ssafy.trip.trip.mapper.TripMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    private final TripMapper tripMapper;

    public TripService(TripMapper tripMapper) {
        this.tripMapper = tripMapper;
    }

    public TripDetailResponse getDetail(Long tripId) {
        //validation
        Trip tripEntity = tripMapper.getDetail(tripId);
        return TripDetailResponse.builder()
                .id(tripEntity.getId())
                .facilityIntroduction(tripEntity.getFacilityIntroduction())
                .facilityName(tripEntity.getFacilityName())
                .phoneNumber(tripEntity.getPhoneNumber())
                .roadAddress(tripEntity.getPlace().getRoadAddress())
                .webPageUrl(tripEntity.getWebPageUrl())
                .latitude(tripEntity.getPlace().getLatitude())
                .longitude(tripEntity.getPlace().getLongitude())
                .streetNumberAddress(tripEntity.getPlace().getStreetNumberAddress())
                .build();
    }

    public Long getTotalCount() {
        return tripMapper.getTotalCount();
    }

    public Long getTotalCountByLocation(Long locationId) {
        return tripMapper.getTotalCountByLocation(locationId);
    }

    public List<TripListResponse> searchAllTripsWithoutPagination() {
        return tripMapper.searchAllTripList();
    }

    public List<TripListResponse> searchAllList(Long currentPage, Long showPage) {
        //validation

        List<Trip> tripList = tripMapper.getAllTripListPagination(currentPage, showPage);
        List<TripListResponse> responseList = new ArrayList<>();

        for (Trip trip : tripList) {
            TripListResponse tripDto = new TripListResponse();
            tripDto.setId(trip.getId());
            tripDto.setPhoneNumber(trip.getPhoneNumber());
            tripDto.setFacilityName(trip.getFacilityName());
            tripDto.setRoadAddress(trip.getPlace().getRoadAddress());
            tripDto.setWebPageUrl(trip.getWebPageUrl());
            responseList.add(tripDto);
        }
        return responseList;
    }

    public List<TripListResponse> searchListByLocate(Long locationId, Long currentPage, Long showPage) {
        //validation

        List<Trip> tripList = tripMapper.getCampTripListByLocate(locationId, currentPage, showPage);
        List<TripListResponse> responseList = new ArrayList<>();

        for (Trip trip : tripList) {
            TripListResponse tripDto = new TripListResponse();
            tripDto.setId(trip.getId());
            tripDto.setPhoneNumber(trip.getPhoneNumber());
            tripDto.setFacilityName(trip.getFacilityName());
            tripDto.setRoadAddress(trip.getPlace().getRoadAddress());
            tripDto.setWebPageUrl(trip.getWebPageUrl());
            responseList.add(tripDto);
        }
        return responseList;
    }


    public List<LocationResponseDto> searchAllLocationList() {
        return tripMapper.searchAllLocationList();
    }
}
