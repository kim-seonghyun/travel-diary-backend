package com.ssafy.trip.travelgraph.controller;

import com.ssafy.trip.travelgraph.dto.response.TravelGraphResponse;
import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/graph")
public class TravelGraphController {

    private TravelGraphService travelGraphService;

    public TravelGraphController(TravelGraphService travelGraphService) {
        this.travelGraphService = travelGraphService;
    }

    @GetMapping("/select")
    public ResponseEntity<TravelGraphResponse> select(HttpSession session) {
        // validation
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
