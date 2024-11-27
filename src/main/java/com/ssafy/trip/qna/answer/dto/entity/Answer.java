package com.ssafy.trip.qna.answer.dto.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Answer {
    private Long id;
    private Long questionId;
    private Long userId;
    private String body;
    private LocalDateTime createdAt;
}