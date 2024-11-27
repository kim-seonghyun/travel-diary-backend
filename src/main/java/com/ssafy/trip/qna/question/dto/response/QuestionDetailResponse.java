package com.ssafy.trip.qna.question.dto.response;

import com.ssafy.trip.qna.answer.dto.response.AnswerResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDetailResponse {
    private QuestionResponse question;
    private List<AnswerResponse> answers;

}
