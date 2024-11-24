package com.ssafy.trip.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentListResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private String username;
    private LocalDateTime createdAt;
}
