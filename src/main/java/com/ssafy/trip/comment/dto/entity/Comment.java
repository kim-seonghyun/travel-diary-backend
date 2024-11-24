package com.ssafy.trip.comment.dto.entity;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private String createdAt;
}
