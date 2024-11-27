package com.ssafy.trip.travelgraph.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DiaryGraphResponse {
    private Long mountain;
    private Long sea;
    private Long valley;
    private Long city;
    private Long festival;
}
