package com.ssafy.trip.user.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRequest {
    private String name;

    private String email;

    private String password;

    private String role;
}
