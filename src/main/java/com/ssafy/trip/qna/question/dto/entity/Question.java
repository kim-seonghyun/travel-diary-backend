package com.ssafy.trip.qna.question.dto.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Question {
    private long id;
    private long userId;
    private String title;
    private String category;
    private String body;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String username;
}
