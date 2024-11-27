package com.ssafy.trip.tosspayment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class ConfirmPaymentRequest {
    private String orderId;
    private String paymentKey;
    private Long amount;
}
