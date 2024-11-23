package com.ssafy.trip.post.dto.request;

import lombok.Data;

@Data
public class PostRegistRequest {
    private Long tripId;
    private String content;
    private Long userId;
    private String postImage;
}
