package com.ssafy.trip.user.controller;

import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.travelgraph.dto.response.TravelGraphResponse;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private TravelGraphService travelGraphService;

    public UserController(UserService userService, TravelGraphService travelGraphService) {
        this.userService = userService;
        this.travelGraphService = travelGraphService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRequest userRequest) {
        //validation
        Long userId = userService.join(userRequest);
        travelGraphService.generateGraph(userId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        //validation
        UserResponse loggedUser = userService.login(email, password);
        session.setAttribute("userInfo", loggedUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loggedUser);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withDrawUser(HttpSession session) {
        //validation
        UserResponse loggedUser = (UserResponse) session.getAttribute("userInfo");
        userService.withdrawUser(loggedUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

//    @GetMapping("/mypage")
//    public ResponseEntity<TravelGraphResponse> mypage(HttpSession session){
//
//        //validation
//
//    }
}
