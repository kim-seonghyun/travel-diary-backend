package com.ssafy.trip.traveldiary.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TravelDiaryListResponse {
    private long id;
    private long userId;
    private String field;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
