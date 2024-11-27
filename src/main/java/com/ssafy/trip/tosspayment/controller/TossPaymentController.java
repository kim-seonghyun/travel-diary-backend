package com.ssafy.trip.tosspayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.tosspayment.dto.request.CancelPaymentRequest;
import com.ssafy.trip.tosspayment.dto.request.ConfirmPaymentRequest;
import com.ssafy.trip.tosspayment.dto.response.BillsResponse;
import com.ssafy.trip.tosspayment.dto.response.PaymentResponse;
import com.ssafy.trip.tosspayment.service.TossPaymentService;
import com.ssafy.trip.user.service.UserService;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/toss")
public class TossPaymentController {

    private final TossPaymentService tossPaymentService;
    // private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public TossPaymentController(TossPaymentService tossPaymentService, JwtUtil jwtUtil, ObjectMapper objectMapper, UserService userService) {
        this.tossPaymentService = tossPaymentService;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        //this.userService = userService;
    }

    @GetMapping("/bills")
    public ResponseEntity<?> getBills(@RequestHeader("Authorization") String accessToken) {

        try {
            accessToken = accessToken.substring(7);
            Claims claim = jwtUtil.parseToken(accessToken);

            List<BillsResponse> bills = tossPaymentService.searchBillsByUserId(claim.get("userId", Long.class));

            return ResponseEntity.status(HttpStatus.OK).body(bills);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody ConfirmPaymentRequest confirmPaymentRequest,
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
            paymentResponse.setPaymentId(confirmPaymentRequest.getPaymentKey());

            if (response.statusCode() == 200) {
                tossPaymentService.savePayment(paymentResponse);
                tossPaymentService.addDotoriToUser(userId, quantity); // 도토리 추가

                return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
            } else {
                throw new IllegalAccessException("결제 서버와 연결되지 않았습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("NULL");
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody CancelPaymentRequest cancelPaymentRequest,
                                    @RequestHeader("Authorization") String accessToken) {

        try {
            accessToken = accessToken.substring(7);
            Claims claim = jwtUtil.parseToken(accessToken);
            Long userId = claim.get("userId", Long.class);

            HttpResponse<String> response = tossPaymentService.paymentCancel(cancelPaymentRequest, userId);


            PaymentResponse paymentResponse = objectMapper.readValue(response.body(), PaymentResponse.class);
            Long quantity = paymentResponse.getQuantity();

            if (response.statusCode() == 200) {
                tossPaymentService.cancelPayment(cancelPaymentRequest.getId());
                tossPaymentService.deleteDotoriToUser(userId, cancelPaymentRequest.getId()); // 도토리 삭제
                return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
            } else {
                throw new IllegalAccessException("결제 취소를 실패했습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("NULL");
    }


}
