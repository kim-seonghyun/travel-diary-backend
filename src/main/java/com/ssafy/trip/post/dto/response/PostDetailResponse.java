package com.ssafy.trip.post.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostDetailResponse {
    private long id;
    private String content;
    private LocalDateTime createdAt;
    private Long tripId;
    private String facilityName;
    private long userId;
    private String username;
    private String postImage;
}
