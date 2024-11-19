package com.ssafy.trip.user.mapper;

import com.ssafy.trip.user.dto.response.RefreshTokenResponse;
import com.ssafy.trip.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserMapper {

    Long join(User user);

    User login(String email, String password);
    // 리프레시토큰 삭제하기.
    void deleteRefreshTokenByUserId(Long userId);

    User selectByUserId(Long userId);

    RefreshTokenResponse searchRefreshTokenByUserId(Long userId);

    void registerRefreshToken(@Param("refreshToken") String refreshToken
            , @Param("userId") Long userId
            , @Param("expiration") Date expiration);

    void deleteUser(Long id);

}
