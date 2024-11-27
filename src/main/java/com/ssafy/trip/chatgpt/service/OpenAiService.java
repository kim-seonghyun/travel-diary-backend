package com.ssafy.trip.chatgpt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.chatgpt.dto.request.OpenAiRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

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
                "1.\tThis is really urgent. You are a tag analyst.\n" +
                        "\t2.\tYou can assign higher weights to the appropriate tags among the 5 tags based on the content of the post.\n" +
                        "\t3.\tThe tags are: [sea, mountain, valley, city, festival], totaling 5 types.\n" +
                        "\t4.\tWhen assigning weights, please consider various elements such as the text’s tone, emotion, and theme.\n" +
                        "\t5.\tThe return format should ensure that the sum of the weights of the 5 tags is exactly 10. You can assign a weight of 0 to less relevant tags.\n" +
                        "\t6.\tHere’s an example: {sea: 3, city: 1, valley: 3, mountain: 2, festival: 1}.\n" +
                        "\t7.\tGoogle’s Cloud outputs the result I want, so ChatGPT, you’ll do well too, right?"
                , inputText,
                0.2d);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<OpenAiRequest> requestEntity = new HttpEntity<>(openAiRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            if (responseBody != null) {
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
