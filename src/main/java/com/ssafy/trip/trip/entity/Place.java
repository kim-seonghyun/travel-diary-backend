package com.ssafy.trip.trip.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    private Long id;
    private String roadAddress;
    private String streetNumberAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
