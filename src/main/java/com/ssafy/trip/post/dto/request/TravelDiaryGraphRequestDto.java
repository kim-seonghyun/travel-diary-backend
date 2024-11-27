package com.ssafy.trip.post.dto.request;

import lombok.Data;

@Data
public class TravelDiaryGraphRequestDto {
    private Long travelDiaryId;
    private int city;
    private int sea;
    private int festival;
    private int valley;
    private int mountain;
}
