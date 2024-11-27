package com.ssafy.trip.user.entity;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    private LocalDateTime createdAt;

    private Long dotori;

    private String profileImg;
}
