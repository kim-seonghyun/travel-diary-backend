package com.ssafy.trip.user.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(String email, String resetToken) {
        String resetLink = "http://localhost:5173/reset/password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("비밀번호 재설정 요청");
        message.setText("다음 링크로 접속하신다음 비밀번호를 재설정 해주세요 \n" + resetLink);
        javaMailSender.send(message);
    }
}
