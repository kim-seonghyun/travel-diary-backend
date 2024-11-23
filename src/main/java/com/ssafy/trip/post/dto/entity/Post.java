package com.ssafy.trip.post.dto.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Post {
    private long id;
    private String content;
    private LocalDateTime createdAt;
    private String postImage;
    private Long userId;
    private Long tripId;
}
