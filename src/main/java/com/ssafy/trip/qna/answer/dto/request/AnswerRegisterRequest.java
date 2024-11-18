package com.ssafy.trip.qna.answer.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AnswerRegisterRequest {
    private Long questionId;
    private Long userId;
    private String body;
    private String imageUrl;
    private LocalDateTime createdAt;
}
