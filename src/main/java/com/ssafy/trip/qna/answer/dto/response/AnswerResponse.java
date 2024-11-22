package com.ssafy.trip.qna.answer.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AnswerResponse {
    private Long id;
    private Long questionId;
    private Long userId;
    private String body;
    private LocalDateTime createdAt;
    private String username;
}
