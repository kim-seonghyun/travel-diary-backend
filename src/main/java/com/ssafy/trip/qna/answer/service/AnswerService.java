package com.ssafy.trip.qna.answer.service;

import com.ssafy.trip.qna.answer.dto.entity.Answer;
import com.ssafy.trip.qna.answer.dto.request.AnswerRegisterRequest;
import com.ssafy.trip.qna.answer.dto.response.AnswerResponse;
import com.ssafy.trip.qna.answer.mapper.AnswerMapper;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerMapper answerMapper;


    public AnswerService(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    public AnswerResponse registerAnswer(AnswerRegisterRequest request) {
        Answer answer = new Answer();
        answer.setBody(request.getBody());
        answer.setQuestionId(request.getQuestionId());
        answer.setUserId(request.getUserId());
        answerMapper.insert(answer);
        return answerMapper.selectByAnswerId(answer.getId());
    }
}
