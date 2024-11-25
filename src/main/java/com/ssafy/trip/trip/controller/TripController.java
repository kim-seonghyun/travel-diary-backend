package com.ssafy.trip.trip.controller;

import com.ssafy.trip.trip.dto.response.LocationResponseDto;
import com.ssafy.trip.trip.dto.response.TripDetailResponse;
import com.ssafy.trip.trip.dto.response.TripListResponse;
import com.ssafy.trip.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<TripListResponse>> getAllTripList(@RequestParam("currentPage") Long currentPage) {
        currentPage = (currentPage - 1) * 16;
        List<TripListResponse> tripList = tripService.searchAllList(currentPage, 16L);
        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @GetMapping("/search-all")
    public ResponseEntity<List<TripListResponse>> getAllTripsWithoutPagination(
    ) {

        List<TripListResponse> tripList = tripService.searchAllTripsWithoutPagination();
        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotalCount() {
        Long totalCount = tripService.getTotalCount();
        return ResponseEntity.status(HttpStatus.OK).body(totalCount);
    }

    @GetMapping("/total/{locationId}")
    public ResponseEntity<Long> getTotalCountByLocation(@PathVariable("locationId") Long locationId) {
        Long totalCount = tripService.getTotalCountByLocation(locationId);
        return ResponseEntity.status(HttpStatus.OK).body(totalCount);
    }

    @Operation(summary = "지역별 여행 목록 조회", description = "특정 지역 ID에 해당하는 여행 목록을 조회합니다.")
    @GetMapping("/search/locate")
    public ResponseEntity<List<TripListResponse>> getTripListByLocate(
            @Parameter(description = "조회할 지역의 ID", required = true) @RequestParam("locationId") Long locationId,
            @RequestParam("currentPage") Long currentPage) {

        currentPage = (currentPage - 1) * 16;

        List<TripListResponse> tripList = tripService.searchListByLocate(locationId, currentPage, 16L);
        return ResponseEntity.status(HttpStatus.OK).body(tripList);
    }

    @Operation(summary = "여행 상세 조회", description = "특정 여행 ID에 대한 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public ResponseEntity<TripDetailResponse> tripDetail(@Parameter(description = "조회할 여행의 ID", required = true)
                                                         @RequestParam("tripId") Long tripId) {

        TripDetailResponse detailResponse = tripService.getDetail(tripId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(detailResponse);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationResponseDto>> searchAllLocationList() {
        List<LocationResponseDto> locationList = tripService.searchAllLocationList();
        return ResponseEntity.status(HttpStatus.OK).body(locationList);
    }

}
