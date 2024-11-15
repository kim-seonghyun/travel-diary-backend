package com.ssafy.trip.purchase.service;

import com.ssafy.trip.purchase.dto.response.PurchaseResponse;
import com.ssafy.trip.purchase.entity.Purchase;
import com.ssafy.trip.purchase.mapper.PurchaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    public void addHistory(Purchase purchase) {
        purchaseMapper.addPurchaseHistory(purchase);
    }

    public boolean isVerifyPurchase(Long traveldiaryId, Long userId){
        Purchase purchaseEntity = purchaseMapper.isVerifyPurchase(traveldiaryId, userId);
        if (purchaseEntity == null) {
            return false;
        }
        return true;
    }

    public List<PurchaseResponse> getList(Long userId) {
        List<Purchase> purchaseEntity = purchaseMapper.getList(userId);

        return purchaseEntity
                .stream()
                .map(PurchaseResponse::convertPurchaseResponse)
                .toList();
    }
}
