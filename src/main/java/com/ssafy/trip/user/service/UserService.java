package com.ssafy.trip.user.service;

import com.ssafy.trip.travelgraph.entity.TravelGraph;
import com.ssafy.trip.travelgraph.mapper.TravelGraphMapper;
import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.user.dto.response.UserMypageResponse;
import com.ssafy.trip.user.entity.User;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final TravelGraphMapper travelGraphMapper;

    public UserService(UserMapper userMapper, TravelGraphMapper travelGraphMapper) {
        this.userMapper = userMapper;
        this.travelGraphMapper = travelGraphMapper;
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

    public UserMypageResponse getMypage(UserResponse user) {
        //validation
        TravelGraph travelGraphEntity = travelGraphMapper.findTravelGraphByUserId(user.getId());
        UserMypageResponse mypage = UserMypageResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dotori(user.getDotori())
                .sea(travelGraphEntity.getSea())
                .city(travelGraphEntity.getCity())
                .festival(travelGraphEntity.getFestival())
                .valley(travelGraphEntity.getValley())
                .mountain(travelGraphEntity.getMountain())
                .build();
        return mypage;
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
