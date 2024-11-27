package com.ssafy.trip.user.controller;

import com.ssafy.trip.travelgraph.service.TravelGraphService;
import com.ssafy.trip.user.dto.request.PasswordResetConfirmRequest;
import com.ssafy.trip.user.dto.request.PasswordResetReqeust;
import com.ssafy.trip.user.dto.request.UserLoginRequest;
import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.user.dto.response.RefreshTokenResponse;
import com.ssafy.trip.user.dto.response.UserMypageResponse;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.service.UserService;
import com.ssafy.trip.utils.ImageUtils;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> login(
            @RequestBody @Parameter(description = "사용자 로그인", required = true) UserLoginRequest userLoginRequest) {
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

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadProfileImg(@RequestHeader("Authorization") String accessToken,
                                                   @RequestPart("image") MultipartFile image) {

        String imageName = ImageUtils.upload(image);
        String token = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(token);
        Long userId = claim.get("userId", Long.class);

        try {
            userService.setProfileImg(userId, imageName);
        } catch (Exception e) {
            throw new IllegalArgumentException("업로드를 다시 시도해 주세요", e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("프로필 이미지 업데이트 완료");
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
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {

        accessToken = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(accessToken);

        try {
            Long userId = claim.get("userId", Long.class);
            UserResponse user = userService.findByUserId(userId);
            if (user != null) {
                userService.deleteRefreshToken(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    // 1. 비밀번호 재설정 요청
    @PostMapping("/reset/password")
    public ResponseEntity<String> requestPasswordReset(@RequestBody PasswordResetReqeust passwordResetReqeust) {
        userService.requestResetPassword(passwordResetReqeust.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 재설정 링크가 이메일로 발송되었습니다.");
    }

    @PostMapping("/reset/confirm-password")
    public ResponseEntity<String> confirmResetPassword(
            @RequestBody PasswordResetConfirmRequest passwordResetConfirmRequest) {

        if (passwordResetConfirmRequest == null) {
            throw new IllegalArgumentException("입력된 비밀번호 변경 정보가 없습니다.");
        }

        try {
            userService.resetPassword(passwordResetConfirmRequest.getToken(),
                    passwordResetConfirmRequest.getNewPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경완료");
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
