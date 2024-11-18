package com.ssafy.trip.qna.question.controller;

import com.ssafy.trip.qna.answer.dto.request.AnswerRegisterRequest;
import com.ssafy.trip.qna.answer.service.AnswerService;
import com.ssafy.trip.qna.question.dto.request.QuestionDto;
import com.ssafy.trip.qna.question.dto.request.QuestionRegisterDto;
import com.ssafy.trip.qna.question.dto.response.QuestionDetailResponse;
import com.ssafy.trip.qna.question.service.QuestionService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<QuestionDto>> getQuestions(){
        List<QuestionDto> questions = questionService.selectAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailResponse> getQuestionDetail(@PathVariable Long id){
        QuestionDetailResponse question = questionService.getQuestionDetail(id);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerQuestion(@RequestBody QuestionRegisterDto questionRegisterDto){
        questionService.insert(questionRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/answer")
    public ResponseEntity<Void> registerAnswer(@RequestBody AnswerRegisterRequest answerDto){
        System.out.println(answerDto);
        answerService.registerAnswer(answerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
