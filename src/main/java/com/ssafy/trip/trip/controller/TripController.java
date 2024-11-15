package com.ssafy.trip.trip.controller;

import com.ssafy.trip.trip.dto.response.TripDetailResponse;
import com.ssafy.trip.trip.dto.response.TripListResponse;
import com.ssafy.trip.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/trip")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @Operation(summary = "전체 여행 목록 조회", description = "모든 여행 목록을 조회합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<TripListResponse>> getAllTripList(){
        List<TripListResponse> tripList = tripService.searchAllList();
        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @Operation(summary = "지역별 여행 목록 조회", description = "특정 지역 ID에 해당하는 여행 목록을 조회합니다.")
    @GetMapping("/search/locate")
    public ResponseEntity<List<TripListResponse>> getTripListByLocate(@Parameter(description = "조회할 지역의 ID", required = true) @RequestParam("locateId") Long locateId){
        List<TripListResponse> tripList = tripService.searchListByLocate(locateId);

        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @Operation(summary = "여행 상세 조회", description = "특정 여행 ID에 대한 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public ResponseEntity<TripDetailResponse> tripDetail(@Parameter(description = "조회할 여행의 ID", required = true)
                                    @RequestParam("tripId") Long tripId){

        TripDetailResponse detailResponse = tripService.getDetail(tripId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(detailResponse);
    }

}
