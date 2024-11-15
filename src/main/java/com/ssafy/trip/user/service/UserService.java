package com.ssafy.trip.user.service;

import com.ssafy.trip.user.dto.request.UserRequest;
import com.ssafy.trip.user.entity.User;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
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

    public UserResponse login(String email, String password) {
        //validation
        User userEntity = userMapper.login(email, password);
        UserResponse loggedUser = UserResponse.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .build();

        return loggedUser;
    }

    public void withdrawUser(Long id) {
        //validation
        userMapper.deleteUser(id);
    }
}
