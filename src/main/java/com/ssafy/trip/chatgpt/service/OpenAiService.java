package com.ssafy.trip.chatgpt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.chatgpt.dto.request.OpenAiRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

    private final String apiKey;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // 생성자 주입
    public OpenAiService(
            @Value("${openai.api.key}") String apiKey,
            ObjectMapper objectMapper,
            RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public String analyzeText(String inputText) throws Exception {

        // JSON 요청 생성
        OpenAiRequest openAiRequest = new OpenAiRequest(
                "당신은 태그 분석가 입니다. Post의 내용을 보고 태그를 뽑아 낼 수 있습니다. 태그의 종류는 [sea, mountain, valley, city, festival] 이렇게 총 5가지이며 지정해준" +
                        "태그 중에서 하나의 요청당 가장 근접한 태그 2~3개를 골라서 태그만 리턴해주세요 sea라는 태그는 '강', '호수' 라는 키워드는 포함하지 않습니다"
                , inputText,
                0.0d);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // HTTP 요청 엔티티 생성
        HttpEntity<OpenAiRequest> requestEntity = new HttpEntity<>(openAiRequest, headers);

        // REST API 호출
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 응답 처리
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            if (responseBody != null) {
                // JSON 파싱
                JsonNode rootNode = objectMapper.readTree(responseBody);
                if (rootNode.has("choices") && rootNode.path("choices").isArray()) {
                    JsonNode firstChoice = rootNode.path("choices").get(0);
                    if (firstChoice != null && firstChoice.has("message")) {
                        JsonNode messageNode = firstChoice.path("message");
                        if (messageNode.has("content")) {
                            return messageNode.path("content").asText();
                        }
                    }
                }
                throw new RuntimeException("Invalid JSON structure in OpenAI response.");
            } else {
                throw new RuntimeException("Response body is null.");
            }
        } else {
            throw new RuntimeException("OpenAI API call failed with status: " + responseEntity.getStatusCode());
        }

    }
}
