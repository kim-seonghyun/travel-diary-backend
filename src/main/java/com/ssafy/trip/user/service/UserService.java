package com.ssafy.trip.user.service;

import com.ssafy.trip.travelgraph.entity.TravelGraph;
import com.ssafy.trip.travelgraph.mapper.TravelGraphMapper;
import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.user.dto.response.RefreshTokenResponse;
import com.ssafy.trip.user.dto.response.UserMypageResponse;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.entity.User;
import com.ssafy.trip.user.mapper.UserMapper;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final TravelGraphMapper travelGraphMapper;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public UserService(UserMapper userMapper, TravelGraphMapper travelGraphMapper, JwtUtil jwtUtil,
                       EmailService emailService) {
        this.userMapper = userMapper;
        this.travelGraphMapper = travelGraphMapper;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    public void updateDotoriByUserId(Long userId, Long newDotori) {

        User user = userMapper.selectByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않은 유저 아이디입니다.");
        }
        userMapper.updateDotoriByUserId(userId, newDotori);
    }

    public void requestResetPassword(String email) {
        String resetToken = jwtUtil.generateResetToken(email);
        emailService.sendPasswordResetEmail(email, resetToken);
        userMapper.saveResetToken(resetToken);
    }

    public boolean validateResetToken(String token) {
        String validationToken = userMapper.findByToken(token);
        if (validationToken == null || jwtUtil.isTokenExpired(validationToken)) {
            return false;
        }
        return true;
    }

    public void resetPassword(String resetToken, String newPassword) {

        if (!validateResetToken(resetToken)) {
            throw new JwtException("잘못된 인증 접근입니다");
        }

        Claims claim = jwtUtil.parseToken(resetToken);
        String email = claim.get("email", String.class);

        if (email == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        User userEntity = userMapper.findByEmail(email);

        if (userEntity == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        userEntity.setPassword(newPassword);
        userMapper.updatePassword(userEntity.getId(), newPassword);
        userMapper.deleteResetToken(resetToken);
    }


    public Long join(UserRequest userRequest) {
        // validation
        User userEntity = User.builder()
                .name(userRequest.getName())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .createdAt(LocalDateTime.now())
                .dotori(0L)
                .build();

        return userMapper.join(userEntity);
    }

    public void setProfileImg(Long userId, String imageName) {
        if (imageName == null || userId == null || userId == 0) {
            throw new IllegalArgumentException("존재하지 않는 이미지 또는 유저 입니다");
        }

        userMapper.setProfileImg(userId, imageName);
    }

    public UserMypageResponse getMypage(UserResponse user) {
        //validation
        TravelGraph travelGraphEntity = travelGraphMapper.findTravelGraphByUserId(user.getId());

        UserMypageResponse mypage = UserMypageResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dotori(user.getDotori())
                .profileImg(user.getProfileImg())
                .sea(travelGraphEntity.getSea())
                .city(travelGraphEntity.getCity())
                .festival(travelGraphEntity.getFestival())
                .valley(travelGraphEntity.getValley())
                .mountain(travelGraphEntity.getMountain())
                .build();
        return mypage;
    }

    public UserResponse findByUserId(Long userId) {

        User userEntity = Optional.ofNullable(userMapper.selectByUserId(userId))
                .orElseThrow(() -> new NoSuchElementException("userEntity가 존재 하지 않음"));

        return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .dotori(userEntity.getDotori())
                .email(userEntity.getEmail())
                .profileImg(userEntity.getProfileImg())
                .build();
    }

    public void registerRefreshToken(String refreshToken, Long userId, Date expiration) {
        if (refreshToken == null) {
            throw new NoSuchElementException("리프레시 토큰이 Null 입니다.");
        }
        userMapper.registerRefreshToken(refreshToken, userId, expiration);
    }

    public Optional<RefreshTokenResponse> searchRefreshToken(Long userId) {
        if (userId == null || userId == 0) {
            throw new NoSuchElementException("존재하지 않는 유저 입니다.");
        }
        return Optional.ofNullable(userMapper.searchRefreshTokenByUserId(userId));
    }

    public void deleteRefreshToken(Long userId) {
        if (userId == null || userId == 0) {
            throw new NoSuchElementException("존재하지 않는 유저 입니다.");
        }
        userMapper.deleteRefreshTokenByUserId(userId);

    }

    public UserResponse login(String email, String password) {
        //validation
        User userEntity = userMapper.login(email, password);
        UserResponse loggedUser = UserResponse.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .dotori(userEntity.getDotori())
                .build();

        return loggedUser;
    }

    public void withdrawUser(Long id) {
        //validation
        userMapper.deleteUser(id);
    }
}
