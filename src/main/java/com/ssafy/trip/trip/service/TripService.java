package com.ssafy.trip.trip.service;

import com.ssafy.trip.trip.dto.response.TripDetailResponse;
import com.ssafy.trip.trip.dto.response.TripListResponse;
import com.ssafy.trip.trip.entity.Trip;
import com.ssafy.trip.trip.mapper.TripMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    private TripMapper tripMapper;

    public TripService(TripMapper tripMapper) {
        this.tripMapper = tripMapper;
    }

    public TripDetailResponse getDetail(Long tripId){
        //validation
        Trip tripEntity = tripMapper.getDetail(tripId);
        return TripDetailResponse.builder()
                .id(tripEntity.getId())
                .facilityIntroduction(tripEntity.getFacilityIntroduction())
                .facilityName(tripEntity.getFacilityName())
                .phoneNumber(tripEntity.getPhoneNumber())
                .roadAddress(tripEntity.getPlace().getRoadAddress())
                .webPageUrl(tripEntity.getWebPageUrl())
                .build();
    }

    public List<TripListResponse> searchAllList(){
        //validation

        List<Trip> tripList = tripMapper.getAllCampTripList();
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

    public List<TripListResponse> searchListByLocate(Long locateId){
        //validation

        List<Trip> tripList = tripMapper.getCampTripListByLocate(locateId);
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


}
