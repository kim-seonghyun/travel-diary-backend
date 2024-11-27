package com.ssafy.trip.user.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PasswordResetConfirmRequest {
    private String token;
    private String newPassword;
}
