package com.ssafy.trip.user.mapper;

import com.ssafy.trip.user.dto.response.RefreshTokenResponse;
import com.ssafy.trip.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface UserMapper {

    Long join(User user);

    @Update("update user set profile_img = #{imageName} where id = #{userId}")
    void setProfileImg(Long userId, String imageName);

    User login(String email, String password);

    @Update("update user set password = #{newPassword} where id = #{id}")
    void updatePassword(Long id, String newPassword);

    User findByEmail(String email);

    @Select("select token from reset_token where token = #{token}")
    String findByToken(String token);

    void deleteRefreshTokenByUserId(Long userId);

    @Select("insert into reset_token(token) values (#{resetToken})")
    void saveResetToken(String resetToken);

    @Delete("delete from reset_token where token = #{resetToken}")
    void deleteResetToken(String resetToken);

    User selectByUserId(Long userId);

    RefreshTokenResponse searchRefreshTokenByUserId(Long userId);

    void registerRefreshToken(@Param("refreshToken") String refreshToken
            , @Param("userId") Long userId
            , @Param("expiration") Date expiration);

    void deleteUser(Long id);

    void updateDotoriByUserId(@Param("userId") Long userId, @Param("newDotori") Long newDotori);

}
