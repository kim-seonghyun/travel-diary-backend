package com.ssafy.trip.travelgraph.entity;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TravelGraph {
    private Long id;
    private Long userId;
    private Long mountain;
    private Long sea;
    private Long valley;
    private Long city;
    private Long festival;
}