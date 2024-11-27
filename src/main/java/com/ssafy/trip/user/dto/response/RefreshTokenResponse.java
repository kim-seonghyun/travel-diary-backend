package com.ssafy.trip.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
public class RefreshTokenResponse {
    private Long id;
    private Long userId;
    private String token;
    private Date createAt;
    private Date expiresAt;
}

