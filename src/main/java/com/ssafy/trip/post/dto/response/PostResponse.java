package com.ssafy.trip.post.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostResponse {
    private long id;
    private String username;
    private String location;
    private LocalDateTime timeAgo;
    private String description;
    private long diaryId;
    private long userId;
    private String postImage;
}
