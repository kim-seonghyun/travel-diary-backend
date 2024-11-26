package com.ssafy.trip.purchase.service;

import com.ssafy.trip.purchase.dto.request.PurchaseRequest;
import com.ssafy.trip.purchase.dto.response.PurchaseResponse;
import com.ssafy.trip.purchase.entity.Purchase;
import com.ssafy.trip.purchase.mapper.PurchaseMapper;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {
    private final PurchaseMapper purchaseMapper;
    private final UserService userService;

    public PurchaseService(PurchaseMapper purchaseMapper, UserService userService) {
        this.purchaseMapper = purchaseMapper;
        this.userService = userService;
    }

    public void addHistory(PurchaseRequest purchaseRequest) {
        purchaseMapper.addPurchaseHistory(purchaseRequest);
    }

    @Transactional
    public void confirmTransaction(PurchaseRequest purchaseRequest) throws IllegalAccessException {

        UserResponse customer = userService.findByUserId(purchaseRequest.getUserId());
        UserResponse owner = userService.findByUserId(purchaseRequest.getOwnerId());


        if (customer == null || owner == null) {
            throw new IllegalAccessException("잘못된 유저 아이디입니다");
        }

        Long userDotori = customer.getDotori();
        if (userDotori < purchaseRequest.getBuyPrice()) {
            throw new IllegalAccessException("결제 할 도토리가 부족합니다");
        }
        // 도토리 거래
        userService.updateDotoriByUserId(customer.getId(), customer.getDotori() - purchaseRequest.getBuyPrice());
        userService.updateDotoriByUserId(owner.getId(), owner.getDotori() + purchaseRequest.getBuyPrice());
    }


    public boolean isVerifyPurchase(Long traveldiaryId, Long userId) {
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
