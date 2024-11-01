package com.ssafy.trip.traveldiary.dto.request;

import lombok.Data;

@Data
public class TravelDiaryUpdateRequest {
    private long id;
    private String title;
    private String description;
}
