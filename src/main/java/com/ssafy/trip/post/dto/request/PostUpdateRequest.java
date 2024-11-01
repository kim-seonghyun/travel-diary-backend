package com.ssafy.trip.post.dto.request;

import lombok.Data;

@Data
public class PostUpdateRequest {
    private long id;
    private String title;
    private String content;
}
