package com.ssafy.trip.qna.question.service;

import com.ssafy.trip.qna.answer.dto.response.AnswerResponse;
import com.ssafy.trip.qna.answer.mapper.AnswerMapper;
import com.ssafy.trip.qna.question.dto.request.QuestionDto;
import com.ssafy.trip.qna.question.dto.request.QuestionRegisterDto;
import com.ssafy.trip.qna.question.dto.response.QuestionDetailResponse;
import com.ssafy.trip.qna.question.dto.response.QuestionResponse;
import com.ssafy.trip.qna.question.mapper.QuestionMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    public QuestionService(QuestionMapper questionMapper, AnswerMapper answerMapper) {
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
    }

    public List<QuestionDto> selectAll() {
        return questionMapper.selectAll();
    }


    public QuestionDetailResponse getQuestionDetail(Long id) {
        QuestionResponse questionResponse = questionMapper.selectById(id);
        List<AnswerResponse> answerResponse = answerMapper.selectByQuestionId(id);

        return new QuestionDetailResponse(questionResponse, answerResponse);
    }

    public void insert(QuestionRegisterDto questionRegisterDto) {
        questionMapper.insert(questionRegisterDto);
    }
}
