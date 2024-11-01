package com.ssafy.trip.post.controller;

import com.ssafy.trip.post.dto.request.PostRegistRequest;
import com.ssafy.trip.post.dto.request.PostUpdateRequest;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.dto.response.PostListResponse;
import com.ssafy.trip.post.service.PostService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/regist")
    public ResponseEntity<Void> regist(@RequestBody PostRegistRequest request){
        postService.regist(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/list")
    public ResponseEntity<List<PostListResponse>> list(){
        List<PostListResponse> posts = postService.list();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }


    @GetMapping("/detail/{postId}")
    public ResponseEntity<PostDetailResponse> detail(@PathVariable Long postId){
        PostDetailResponse post = postService.detail(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody PostUpdateRequest request){
        postService.update(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId){
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
