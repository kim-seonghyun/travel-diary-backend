package com.ssafy.trip.traveldiary.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TravelDiaryListResponse {
    private long id;
    private long userId;
    private String username;
    private String title;
    private String imageName;
    private String description;
    private LocalDateTime createdAt;
    private Long matchScore;
}
