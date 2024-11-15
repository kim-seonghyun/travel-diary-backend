package com.ssafy.trip.purchase.dto.response;

import com.ssafy.trip.purchase.entity.Purchase;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Builder
public class PurchaseResponse {
    private Long traveldiaryId;
    private Long userId;
    private LocalDateTime purchaseAt;
    private Long buyPrice;

    public static PurchaseResponse convertPurchaseResponse(Purchase purchase) {
        return PurchaseResponse.builder()
                .traveldiaryId(purchase.getTraveldiaryId())
                .userId(purchase.getId())
                .purchaseAt(purchase.getPurchaseAt())
                .buyPrice(purchase.getBuyPrice())
                .build();
    }
}
