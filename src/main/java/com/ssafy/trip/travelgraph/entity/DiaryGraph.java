package com.ssafy.trip.travelgraph.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DiaryGraph {
    private Long id;
    private Long travelDiaryId;
    private Long mountain;
    private Long sea;
    private Long valley;
    private Long city;
    private Long festival;
}
