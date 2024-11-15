package com.ssafy.trip.purchase.controller;

import com.ssafy.trip.purchase.dto.response.PurchaseResponse;
import com.ssafy.trip.purchase.service.PurchaseService;
import com.ssafy.trip.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/verify/{traveldiaryId}")
    public ResponseEntity<String> verifyPurchase(@PathVariable Long traveldiaryId, HttpSession session) {

        UserResponse loggedUser = (UserResponse) session.getAttribute("userInfo");
        boolean verifyPurchase = purchaseService.isVerifyPurchase(traveldiaryId, loggedUser.getId());

        if (!verifyPurchase) {
            throw new NoSuchElementException("구매 후 확인 가능합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("구매되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<PurchaseResponse> getPurchaseList(HttpSession session) {

        UserResponse loggedUser = (UserResponse) session.getAttribute("userInfo");
        List<PurchaseResponse> responsePurchaseList = purchaseService.getList(loggedUser.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body((PurchaseResponse) responsePurchaseList);
    }


}
