package com.ssafy.trip.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Long dotori;
    private String profileImg;
}
