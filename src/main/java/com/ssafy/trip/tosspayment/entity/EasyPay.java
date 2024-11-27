package com.ssafy.trip.tosspayment.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EasyPay {
    private String provider;
    private Long amount;
    private Long discountAmount;
}
