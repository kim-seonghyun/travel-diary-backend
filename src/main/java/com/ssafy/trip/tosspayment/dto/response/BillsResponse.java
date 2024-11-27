package com.ssafy.trip.tosspayment.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
public class BillsResponse {
    private Long id;
    private Date rechargeAt;
    private Long quantity;
    private String orderName;
    private String status;
    private String provider;
    private Long amount;
    private String paymentId;
}
