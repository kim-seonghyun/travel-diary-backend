package com.ssafy.trip.comment.service;

import com.ssafy.trip.comment.dto.entity.Comment;
import com.ssafy.trip.comment.dto.request.CommentRegisterDto;
import com.ssafy.trip.comment.dto.response.CommentResponseDto;
import com.ssafy.trip.comment.mapper.CommentMapper;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentMapper mapper;

    public CommentService(CommentMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public CommentResponseDto registerComment(CommentRegisterDto commentRegisterDto) {
        Comment comment = new Comment();
        comment.setUserId(commentRegisterDto.getUserId());
        comment.setPostId(commentRegisterDto.getPostId());
        comment.setContent(commentRegisterDto.getContent());


        mapper.registerComment(comment);

        CommentResponseDto response = new CommentResponseDto();
        response.setId(comment.getId());
        response.setUserId(comment.getUserId());
        response.setPostId(comment.getPostId());
        response.setContent(comment.getContent());
        response.setCreatedAt(LocalDateTime.now());

        return response;
    }
}
