package com.ssafy.trip.post.controller;

import com.ssafy.trip.post.dto.request.PostRegistRequest;
import com.ssafy.trip.post.dto.request.PostUpdateRequest;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.dto.response.PostListResponse;
import com.ssafy.trip.post.dto.response.PostLocationResponseDto;
import com.ssafy.trip.post.service.PostService;
import com.ssafy.trip.utils.ImageUtils;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Post API", description = "Post 관련 API")
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    public PostController(PostService postService, JwtUtil jwtUtil) {
        this.postService = postService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/location-list/{userId}")
    public ResponseEntity<List<PostLocationResponseDto>> getPostLocation(@PathVariable Long userId) {
        List<PostLocationResponseDto> postLocation = postService.getPostLocation(userId);
        return ResponseEntity.status(HttpStatus.OK).body(postLocation);
    }

    @Operation(summary = "Post 등록", description = "이 API는 새로운 Post를 등록합니다.")
    @PostMapping(value = "/regist")
    public ResponseEntity<PostDetailResponse> regist(@Parameter(description = "Post 등록 요청 객체", required = true)
                                                     @RequestPart(value = "image") MultipartFile image,
                                                     @RequestPart(value = "request") PostRegistRequest request) {
        String filename = ImageUtils.upload(image);
        request.setPostImage(filename);
        PostDetailResponse response = postService.regist(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Post 목록 조회", description = "이 API는 모든 Post 목록을 반환합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<PostListResponse>> list() {
        List<PostListResponse> posts = postService.list();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/list/all/{tripId}")
    public ResponseEntity<List<PostListResponse>> getListByTripId(@PathVariable Long tripId) {
        List<PostListResponse> posts = postService.getListByTripId(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/list/{tripId}")
    public ResponseEntity<List<PostListResponse>> getListOnlyThree(@PathVariable Long tripId) {
        List<PostListResponse> posts = postService.getListOnlyThree(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/list/all/{tripId}/like")
    public ResponseEntity<List<PostListResponse>> getListByTripIdOrderByLikes(@PathVariable Long tripId) {
        List<PostListResponse> posts = postService.getListByTripIdOrderByLikes(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/list/{tripId}/like")
    public ResponseEntity<List<PostListResponse>> getListByTripIdOrderByLikesOnlyThree(@PathVariable Long tripId) {
        List<PostListResponse> posts = postService.getListByTripIdOrderByLikesOnlyThree(tripId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @Operation(summary = "Post 상세 조회", description = "이 API는 특정 Post의 상세 정보를 반환합니다.")
    @GetMapping("/detail/{postId}")
    public ResponseEntity<PostDetailResponse> detail(@PathVariable Long postId) {
        PostDetailResponse post = postService.detail(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @Operation(summary = "Post 업데이트", description = "이 API는 기존 Post를 업데이트합니다.")
    @PutMapping("/update")
    public ResponseEntity<Void> update(@Parameter(description = "조회할 Post의 ID", required = true)
                                       @RequestBody PostUpdateRequest request) {
        postService.update(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Post 삭제", description = "이 API는 특정 Post를 삭제합니다.")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> delete(@Parameter(description = "삭제할 Post의 ID", required = true)
                                       @PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}/increment-view")
    public ResponseEntity<Void> incrementView(@RequestHeader("Authorization") String accessToken,
                                              @PathVariable Long postId) {
        accessToken = accessToken.substring(7);
        Claims claims = jwtUtil.parseToken(accessToken);
        Long userId = claims.get("userId", Long.class);

        postService.incrementView(postId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}/increment-likes")
    public ResponseEntity<Void> incrementLikes(@RequestHeader("Authorization") String accessToken,
                                               @PathVariable Long postId) {
        accessToken = accessToken.substring(7);
        Claims claims = jwtUtil.parseToken(accessToken);
        Long userId = claims.get("userId", Long.class);

        postService.incrementLikes(postId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
