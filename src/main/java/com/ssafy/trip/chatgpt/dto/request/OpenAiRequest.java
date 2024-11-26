package com.ssafy.trip.chatgpt.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAiRequest {

    private final String model = "gpt-3.5-turbo";
    private final Message[] messages;
    private final double temperature; // temperature 추가

    public OpenAiRequest(String systemInstruction, String content, double temperature) {
        this.messages = new Message[]{
                new Message("system", systemInstruction), // 초기 역할 설정
                new Message("user", content)             // 사용자 입력
        };
        this.temperature = temperature;
    }


    // 내부 클래스 정의
    @Getter
    public static class Message {
        private final String role;
        private final String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

    }

}
