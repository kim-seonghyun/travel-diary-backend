package com.ssafy.trip.tosspayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.tosspayment.dto.request.ConfirmPaymentRequest;
import com.ssafy.trip.tosspayment.dto.response.PaymentResponse;
import com.ssafy.trip.tosspayment.service.TossPaymentService;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/toss")
public class TossPaymentController {

    private final TossPaymentService tossPaymentService;
    private final JwtUtil jwtUtil;
    private ObjectMapper objectMapper;

    public TossPaymentController(TossPaymentService tossPaymentService, JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.tossPaymentService = tossPaymentService;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confrim(@RequestBody ConfirmPaymentRequest confirmPaymentRequest,
                                     @RequestHeader("Authorization") String accessToken) {

        try {
            HttpResponse<String> response = tossPaymentService.paymentConfirm(confirmPaymentRequest);
            accessToken = accessToken.substring(7);
            Claims claim = jwtUtil.parseToken(accessToken);
            Long userId = claim.get("userId", Long.class);

            PaymentResponse paymentResponse = objectMapper.readValue(response.body(), PaymentResponse.class);
            Long quantity = confirmPaymentRequest.getAmount();

            if (quantity == 1000) {
                quantity = 10L;
            } else if (quantity == 4000) {
                quantity = 50L;
            } else {
                quantity = 100L;
            }

            paymentResponse.setUserId(userId);
            paymentResponse.setQuantity(quantity);
            tossPaymentService.savePayment(paymentResponse);

            if (response.statusCode() == 200) {
                tossPaymentService.savePayment(paymentResponse);
                return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
            } else {
                throw new IllegalAccessException("결제 서버와 연결되지 않았습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("NULL");
    }
}
