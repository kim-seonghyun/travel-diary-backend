package com.ssafy.trip.post.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostListResponse {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long diaryId;
    private long userId;
}
