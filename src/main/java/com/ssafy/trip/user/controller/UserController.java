package com.ssafy.trip.user.controller;

import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.request.UserLoginRequest;
import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.user.dto.response.RefreshTokenResponse;
import com.ssafy.trip.user.dto.response.UserMypageResponse;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.service.UserService;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final TravelGraphService travelGraphService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, TravelGraphService travelGraphService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.travelGraphService = travelGraphService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록하고 여행 그래프를 생성합니다.")
    @PostMapping("/join")
    public ResponseEntity<String> join(@Parameter(description = "회원가입 요청 정보", required = true)
                                       @RequestBody UserRequest userRequest) {
        //validation
        Long userId = userService.join(userRequest);
        UserResponse loggedUser = userService.login(userRequest.getEmail(), userRequest.getPassword());
        travelGraphService.generateGraph(loggedUser.getId());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 사용하여 로그인하고 세션에 사용자 정보를 저장합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Parameter(description = "사용자 로그인", required = true) UserLoginRequest userLoginRequest) {
        //validation
        UserResponse loggedUser = userService.login(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        List<String> role = List.of("user");
        String accessToken = jwtUtil.generateAccessToken(loggedUser, role);
        String refreshToken = jwtUtil.generateRefreshToken(loggedUser, role);

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        map.put("user", loggedUser);

        Claims claims = jwtUtil.parseToken(refreshToken);

        // DB에 refreshToken 저장하기
        userService.registerRefreshToken(refreshToken, loggedUser.getId(), claims.getExpiration());

        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }

    @Operation(summary = "회원 탈퇴", description = "현재 세션의 사용자 정보를 바탕으로 해당 사용자를 탈퇴 처리합니다.")
    @PostMapping("/withdraw")
    public ResponseEntity<String> withDrawUser(HttpSession session) {
        //validation
        UserResponse loggedUser = (UserResponse) session.getAttribute("userInfo");
        userService.withdrawUser(loggedUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Long userId) {
        UserResponse user = userService.findByUserId(userId);
        userService.deleteRefreshToken(userId);

        // 완전한 로그아웃하려면 클라이언트에 저장된 토큰도 제거해야함.
        return ResponseEntity.status(HttpStatus.OK).body("logout complete");
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserMypageResponse> mypage(@RequestHeader("Authorization") String accessToken) {
        //validation
        accessToken = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(accessToken);

        UserResponse loggedUser = userService.findByUserId(claim.get("userId", Long.class));

        UserMypageResponse mypage = userService.getMypage(loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(mypage);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refresh(@RequestBody String refreshToken) throws IllegalAccessException {
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new IllegalAccessException("리프레시 토큰이 만료되었습니다");
        }
        Claims claims = jwtUtil.parseToken(refreshToken);
        List<String> role = List.of("user"); // 임시방편

        RefreshTokenResponse storedToken =
                userService.searchRefreshToken(claims.get("userId", Long.class))
                        .orElseThrow(() -> new IllegalAccessException("유효하지 않는 리프레시 토큰입니다"));


        UserResponse userResponse = UserResponse.builder()
                .id(storedToken.getId())
                .name(storedToken.getToken())
                .build();
        // 리프레시토큰을 사용하여 엑세스토큰 생성
        String newAccessToken = jwtUtil.generateAccessToken(userResponse, role);
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", newAccessToken);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
