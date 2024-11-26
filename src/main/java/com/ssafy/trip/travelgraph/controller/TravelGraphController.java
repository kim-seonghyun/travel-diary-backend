package com.ssafy.trip.travelgraph.controller;

import com.ssafy.trip.travelgraph.dto.response.TravelGraphResponse;
import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api/graph")
public class TravelGraphController {

    private TravelGraphService travelGraphService;

    public TravelGraphController(TravelGraphService travelGraphService) {
        this.travelGraphService = travelGraphService;
    }

    @Operation(summary = "여행 그래프 조회", description = "현재 로그인한 사용자의 여행 그래프를 조회합니다.")
    @GetMapping("/select")
    public ResponseEntity<TravelGraphResponse> select(@Parameter(description = "세션 정보", required = true, hidden = true) HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("userInfo");
        TravelGraphResponse travelGraph = travelGraphService.getTravelGraph(user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelGraph);
    }



    @PostMapping("/update")
    public ResponseEntity<Void> updateDegree(@RequestBody List<String> taglist, HttpSession session) {
        // validation
        Map<String, Object> map = new HashMap<>();
        UserResponse user = (UserResponse) session.getAttribute("userInfo");
        map.put("tagList", taglist);
        map.put("userId", user.getId());

        travelGraphService.calculateTravelDegree(map);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
