package com.ssafy.trip.trip.controller;

import com.ssafy.trip.trip.dto.response.TripDetailResponse;
import com.ssafy.trip.trip.dto.response.TripListResponse;
import com.ssafy.trip.trip.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<TripListResponse>> getAllTripList(){
        //validation
        List<TripListResponse> tripList = tripService.searchAllList();
        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @GetMapping("/search/locate")
    public ResponseEntity<List<TripListResponse>> getTripListByLocate(@RequestParam("locateId") Long locateId){
        //validation
        List<TripListResponse> tripList = tripService.searchListByLocate(locateId);

        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @GetMapping("/detail")
    public ResponseEntity<TripDetailResponse> tripDetail(@RequestParam("tripId") Long tripId){

        TripDetailResponse detailResponse = tripService.getDetail(tripId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(detailResponse);
    }

}
