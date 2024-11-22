package com.ssafy.trip.tosspayment.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CancelPaymentRequest {
    private Long id;
    private String cancelReason;
    private String paymentId;
}
