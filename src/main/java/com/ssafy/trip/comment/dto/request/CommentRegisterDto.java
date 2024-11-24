package com.ssafy.trip.comment.dto.request;

import lombok.Data;

@Data
public class CommentRegisterDto {
    private Long userId;
    private Long postId;
    private String content;
}
