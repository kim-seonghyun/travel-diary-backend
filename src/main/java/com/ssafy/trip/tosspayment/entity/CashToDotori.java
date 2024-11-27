package com.ssafy.trip.tosspayment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
public class CashToDotori {
    private Long id;
    private Long userId;
    private Date rechargeAt;
    private Long quantity;
    private String orderId;
    private String orderName;
    private String status;
    private String provider;
    private Long amount;
    private Long discountAmount;
    private String paymentId;
}
