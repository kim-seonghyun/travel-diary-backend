package com.ssafy.trip.purchase.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseRequest {
    private Long traveldiaryId;
    private Long ownerId;
    private Long userId;
    private Long buyPrice;
}
