package com.ssafy.trip.user.mapper;

import com.ssafy.trip.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Long join(User user);

    User login(String email, String password);

    void deleteUser(Long id);

}
