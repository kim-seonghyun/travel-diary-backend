package com.ssafy.trip.hashtag.controller;

import com.ssafy.trip.hashtag.dto.request.HashtagDeleteRequest;
import com.ssafy.trip.hashtag.dto.request.HashtagRegistRequest;
import com.ssafy.trip.hashtag.service.HashtagService;
import com.ssafy.trip.post.dto.response.PostListResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hashtag")
public class HashtagController {
    private final HashtagService service;

    public HashtagController(HashtagService service) {
        this.service = service;
    }

    @PostMapping("/regist")
    public ResponseEntity<Void> regist(@RequestBody HashtagRegistRequest request){
        service.regist(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody HashtagDeleteRequest request){
        service.delete(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{hashtagId}")
    public ResponseEntity<List<PostListResponse>> search(@PathVariable Long hashtagId){
        List<PostListResponse> responses = service.search(hashtagId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
