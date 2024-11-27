package com.ssafy.trip.tosspayment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.tosspayment.dto.request.CancelPaymentRequest;
import com.ssafy.trip.tosspayment.dto.request.ConfirmPaymentRequest;
import com.ssafy.trip.tosspayment.dto.response.BillsResponse;
import com.ssafy.trip.tosspayment.dto.response.PaymentResponse;
import com.ssafy.trip.tosspayment.entity.CashToDotori;
import com.ssafy.trip.tosspayment.mapper.TossPaymentMapper;
import com.ssafy.trip.user.dto.response.UserResponse;
import com.ssafy.trip.user.entity.User;
import com.ssafy.trip.user.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TossPaymentService {
    private final TossPaymentMapper tossPaymentMapper;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public TossPaymentService(TossPaymentMapper tossPaymentMapper, UserService userService, ObjectMapper objectMapper) {
        this.tossPaymentMapper = tossPaymentMapper;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public void savePayment(PaymentResponse paymentResponse) {
        if(paymentResponse == null) {
            throw new NoSuchElementException("구매내역이 없습니다");
        }

        CashToDotori cashToDotoriEntity = CashToDotori.builder()
                .amount(paymentResponse.getEasyPay().getAmount())
                .discountAmount(paymentResponse.getEasyPay().getDiscountAmount())
                .provider(paymentResponse.getEasyPay().getProvider())
                .quantity(paymentResponse.getQuantity())
                .status(paymentResponse.getStatus())
                .rechargeAt(new Date())
                .orderId(paymentResponse.getOrderId())
                .orderName(paymentResponse.getOrderName())
                .userId(paymentResponse.getUserId())
                .paymentId(paymentResponse.getPaymentId())
                .build();

        tossPaymentMapper.savePayment(cashToDotoriEntity);
    }

    public void cancelPayment(Long id) throws IllegalAccessException {
        if(id == null || id == 0){
            throw new IllegalAccessException("잘못된 접근입니다");
        }
        tossPaymentMapper.cancelPayment(id);
    }

    public List<BillsResponse> searchBillsByUserId(Long userId) throws IllegalAccessException {
        if(userId == null || userId == 0){
            throw new IllegalAccessException("잘못된 접근입니다");
        }

        return tossPaymentMapper.searchBillsByUserId(userId);
    }

    public void addDotoriToUser(Long userId, Long quantity) {

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("존재하지 않거나 잘못된 유저 아이디입니다");
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("도토리 수량은 1 이상이어야 합니다.");
        }

        try{
            tossPaymentMapper.addDotoriToUser(userId, quantity);
        }catch(Exception e) {
            throw new RuntimeException("도토리 추가에 실패했습니다.", e);
        }
    }

    public void deleteDotoriToUser(Long userId, Long id) {

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("존재하지 않거나 잘못된 유저 아이디입니다");
        }

        Long quantity = tossPaymentMapper.getQuantityDotori(id);

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("도토리 수량은 1 이상이어야 합니다.");
        }

        try{
            tossPaymentMapper.deleteDotoriToUser(userId, quantity);
        }catch(Exception e) {
            throw new RuntimeException("도토리 제거에 실패했습니다.", e);
        }
    }


    public HttpResponse<String> paymentConfirm(ConfirmPaymentRequest confirmPaymentRequest) throws IOException, InterruptedException {
        JsonNode json = objectMapper.createObjectNode()
                .put("orderId", confirmPaymentRequest.getOrderId())
                .put("paymentKey", confirmPaymentRequest.getPaymentKey())
                .put("amount", confirmPaymentRequest.getAmount().intValue());

        String requestBody = objectMapper.writeValueAsString(json);
        // 이거 숨겨야함;;;
        String secreteKey = "test_sk_ex6BJGQOVDk9Mw4M99eO3W4w2zNb";
        String auth = Base64.getEncoder().encodeToString((secreteKey+":").getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic "+ auth)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> paymentCancel(CancelPaymentRequest cancelPaymentRequest, Long userId) throws IOException, InterruptedException, IllegalAccessException {

        UserResponse user = userService.findByUserId(userId);
        Long quantity = tossPaymentMapper.getQuantityDotori(cancelPaymentRequest.getId());

        if (user.getDotori() < quantity) {
            throw new IllegalAccessException("환불 불가 : 소유하신 도토리가 부족합니다");
        }

        JsonNode json = objectMapper.createObjectNode()
                .put("cancelReason", cancelPaymentRequest.getCancelReason());

        String requestBody = objectMapper.writeValueAsString(json);
        // 이거 숨겨야함;;;
        String secreteKey = "test_sk_ex6BJGQOVDk9Mw4M99eO3W4w2zNb";
        String auth = Base64.getEncoder().encodeToString((secreteKey+":").getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/"+cancelPaymentRequest.getPaymentId()+"/cancel"))
                .header("Authorization", "Basic "+ auth)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
