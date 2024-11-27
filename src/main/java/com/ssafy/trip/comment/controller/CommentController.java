package com.ssafy.trip.comment.controller;

import com.ssafy.trip.comment.dto.request.CommentRegisterDto;
import com.ssafy.trip.comment.dto.response.CommentResponseDto;
import com.ssafy.trip.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/register")
    public ResponseEntity<CommentResponseDto> registerComment(@RequestBody CommentRegisterDto commentRegisterDto) {
        CommentResponseDto dto = commentService.registerComment(commentRegisterDto);

        return ResponseEntity.ok().body(dto);
    }

}
