package com.ssafy.trip.traveldiary.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TravelDiaryRegistRequest {
    private String title;
    private String field;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long userId;

    public TravelDiaryRegistRequest(String field, String title, String description,
                                    long userId) {
        this.field = field;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = userId;
    }
}
