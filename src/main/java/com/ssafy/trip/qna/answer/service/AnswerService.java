package com.ssafy.trip.qna.answer.service;

import com.ssafy.trip.qna.answer.dto.request.AnswerRegisterRequest;
import com.ssafy.trip.qna.answer.dto.response.AnswerResponse;
import com.ssafy.trip.qna.answer.mapper.AnswerMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerMapper answerMapper;


    public AnswerService(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    public List<AnswerResponse> getAnswerDetail(long questionId){
        return answerMapper.selectByQuestionId(questionId);
    }

    public void registerAnswer(AnswerRegisterRequest request){
        answerMapper.insert(request);
    }
}
