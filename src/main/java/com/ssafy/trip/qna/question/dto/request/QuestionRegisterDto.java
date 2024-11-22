package com.ssafy.trip.qna.question.dto.request;

import lombok.Data;

@Data
public class QuestionRegisterDto {
    private long userId;
    private String title;
    private String category;
    private String body;
    private String imageUrl;
}
