package com.ssafy.trip.purchase.controller;

import com.ssafy.trip.purchase.dto.request.PurchaseRequest;
import com.ssafy.trip.purchase.dto.response.PurchaseResponse;
import com.ssafy.trip.purchase.service.PurchaseService;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final JwtUtil jwtUtil;

    public PurchaseController(PurchaseService purchaseService, JwtUtil jwtUtil) {
        this.purchaseService = purchaseService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/verify/{travelDiaryId}")
    public ResponseEntity<String> verifyPurchase(@PathVariable Long travelDiaryId, HttpSession session) {

        UserResponse loggedUser = (UserResponse) session.getAttribute("userInfo");
        boolean verifyPurchase = purchaseService.isVerifyPurchase(travelDiaryId, loggedUser.getId());

        if (!verifyPurchase) {
            throw new NoSuchElementException("구매 후 확인 가능합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("구매되었습니다.");
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirmTransaction(@RequestHeader("Authorization") String accessToken,
                                                @RequestBody PurchaseRequest purchaseRequest) {
        accessToken = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(accessToken);
        Long userId = claim.get("userId", Long.class);
        purchaseRequest.setUserId(userId);

        try {
            purchaseService.confirmTransaction(purchaseRequest);
            purchaseService.addHistory(purchaseRequest);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("구매되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<PurchaseResponse> getPurchaseList(@RequestHeader("Authorization") String accessToken) {
        accessToken = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(accessToken);
        Long userId = claim.get("userId", Long.class);

        List<PurchaseResponse> responsePurchaseList = purchaseService.getList(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body((PurchaseResponse) responsePurchaseList);
    }
}
