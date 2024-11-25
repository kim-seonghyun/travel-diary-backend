package com.ssafy.trip.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserMypageResponse {
    private Long id;
    private String name;
    private String email;
    private Long dotori;
    private Long mountain;
    private Long sea;
    private Long valley;
    private Long city;
    private Long festival;
    private String profileImg;
}
