package com.ssafy.trip.user.controller;

import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final TravelGraphService travelGraphService;

    public UserController(UserService userService, TravelGraphService travelGraphService) {
        this.userService = userService;
        this.travelGraphService = travelGraphService;
    }
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록하고 여행 그래프를 생성합니다.")
    @PostMapping("/join")
    public ResponseEntity<String> join(@Parameter(description = "회원가입 요청 정보", required = true)
                                                   @RequestBody UserRequest userRequest) {
        //validation
        Long userId = userService.join(userRequest);
        travelGraphService.generateGraph(userId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 사용하여 로그인하고 세션에 사용자 정보를 저장합니다.")
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Parameter(description = "사용자 이메일", required = true)  @RequestParam("email") String email,@Parameter(description = "사용자 비밀번호", required = true)  @RequestParam("password") String password, HttpSession session) {
        //validation
        UserResponse loggedUser = userService.login(email, password);
        session.setAttribute("userInfo", loggedUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loggedUser);
    }
    @Operation(summary = "회원 탈퇴", description = "현재 세션의 사용자 정보를 바탕으로 해당 사용자를 탈퇴 처리합니다.")
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
