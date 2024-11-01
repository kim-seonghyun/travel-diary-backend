package com.ssafy.trip.travelgraph.controller;

import com.ssafy.trip.travelgraph.dto.response.TravelGraphResponse;
import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
