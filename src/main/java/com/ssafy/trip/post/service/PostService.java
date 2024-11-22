package com.ssafy.trip.post.service;

import com.ssafy.trip.post.dto.request.PostRegistRequest;
import com.ssafy.trip.post.dto.request.PostUpdateRequest;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.dto.response.PostListResponse;
import com.ssafy.trip.post.mapper.PostMapper;
import com.ssafy.trip.posthashtag.mapper.PostHashtagMapper;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostMapper mapper;
    private final PostHashtagMapper postHashtagMapper;

    public PostService(PostMapper mapper, PostHashtagMapper postHashtagMapper) {
        this.mapper = mapper;
        this.postHashtagMapper = postHashtagMapper;
    }

    public void regist(PostRegistRequest request) {
        mapper.regist(request);
    }

    public List<PostListResponse> list() {
        return mapper.list();
    }

    public List<PostListResponse> getListByTripId(Long tripId) {
        return mapper.getListByTripId(tripId);
    }

    public List<PostListResponse> getListOnlyThree(Long tripId) {
        return mapper.getListOnlyThree(tripId);
    }

    public PostDetailResponse detail(Long postId) {
        return mapper.selectById(postId);
    }

    public void update(PostUpdateRequest request) {
        mapper.update(request);
    }

    public void delete(Long postId) {
        postHashtagMapper.deleteByPostId(postId);
        mapper.deleteByPostId(postId);
    }
}
