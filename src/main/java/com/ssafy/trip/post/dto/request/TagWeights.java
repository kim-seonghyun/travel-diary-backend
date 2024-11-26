package com.ssafy.trip.post.dto.request;

import lombok.Data;

@Data
public class TagWeights {
    private Long userId;
    private int sea;
    private int mountain;
    private int valley;
    private int city;
    private int festival;
}
