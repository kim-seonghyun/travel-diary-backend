package com.ssafy.trip.hashtag.service;

import com.ssafy.trip.hashtag.dto.entity.Hashtag;
import com.ssafy.trip.hashtag.dto.request.HashtagDeleteRequest;
import com.ssafy.trip.hashtag.dto.request.HashtagRegistRequest;
import com.ssafy.trip.hashtag.mapper.HashtagMapper;
import com.ssafy.trip.post.dto.response.PostListResponse;
import com.ssafy.trip.posthashtag.dto.entity.PostHashtag;
import com.ssafy.trip.posthashtag.mapper.PostHashtagMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {
    private final HashtagMapper mapper;
    private final PostHashtagMapper postHashtagMapper;

    public HashtagService(HashtagMapper mapper, PostHashtagMapper postHashtagMapper) {
        this.mapper = mapper;
        this.postHashtagMapper = postHashtagMapper;
    }

    public void regist(HashtagRegistRequest request) {
        Hashtag hashtag = new Hashtag();
        hashtag.setTag(request.getTag());
        mapper.regist(hashtag);
        PostHashtag postHashtag = new PostHashtag();
        postHashtag.setHashtagId(hashtag.getId());
        postHashtag.setPostId(request.getPostId());
        postHashtagMapper.regist(postHashtag);
    }

    public void delete(HashtagDeleteRequest request) {
        postHashtagMapper.deleteByPostId(request.getId());
        mapper.delete(request.getId());
    }

    public List<PostListResponse> search(Long hashtagId) {
        return postHashtagMapper.selectByHashtagId(hashtagId);

    }
}
