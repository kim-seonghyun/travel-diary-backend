package com.ssafy.trip.tosspayment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.trip.tosspayment.entity.EasyPay;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    private Long userId;
    private String orderId;
    private String orderName;
    private Long quantity;
    private String status;
    private String paymentId;
    private EasyPay easyPay;
}
