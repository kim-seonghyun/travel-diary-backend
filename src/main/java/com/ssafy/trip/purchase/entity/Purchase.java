package com.ssafy.trip.purchase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class Purchase {
    private Long id;
    private Long traveldiaryId;
    private Long userId;
    private LocalDateTime purchaseAt;
    private Long buyPrice;
}


