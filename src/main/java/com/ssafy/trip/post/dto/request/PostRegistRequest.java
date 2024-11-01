package com.ssafy.trip.post.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostRegistRequest {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long diaryId;
    private long userId;
}
