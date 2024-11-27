package com.ssafy.trip.post.service;

import com.ssafy.trip.chatgpt.service.OpenAiService;
import com.ssafy.trip.comment.dto.response.CommentListResponse;
import com.ssafy.trip.comment.mapper.CommentMapper;
import com.ssafy.trip.post.dto.entity.Post;
import com.ssafy.trip.post.dto.request.PostRegistRequest;
import com.ssafy.trip.post.dto.request.PostUpdateRequest;
import com.ssafy.trip.post.dto.request.TagWeights;
import com.ssafy.trip.post.dto.request.TravelDiaryGraphRequestDto;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.dto.response.PostListResponse;
import com.ssafy.trip.post.dto.response.PostLocationResponseDto;
import com.ssafy.trip.post.mapper.PostMapper;
import com.ssafy.trip.post.mapper.TravelDiaryGraphMapper;
import com.ssafy.trip.posthashtag.mapper.PostHashtagMapper;
import com.ssafy.trip.travelgraph.mapper.TravelGraphMapper;
import com.ssafy.trip.trip.mapper.TripMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostMapper mapper;
    private final PostHashtagMapper postHashtagMapper;
    private final CommentMapper commentMapper;
    private final TripMapper tripMapper;
    private final OpenAiService openAiService;
    private final TravelDiaryGraphMapper travelDiaryGraphMapper;
    private final TravelGraphMapper travelGraphMapper;

    public PostService(PostMapper mapper, PostHashtagMapper postHashtagMapper, CommentMapper commentMapper,
                       TripMapper tripMapper, OpenAiService openAiService,
                       TravelDiaryGraphMapper travelDiaryGraphMapper,
                       TravelGraphMapper travelGraphMapper) {
        this.mapper = mapper;
        this.postHashtagMapper = postHashtagMapper;
        this.commentMapper = commentMapper;
        this.tripMapper = tripMapper;
        this.openAiService = openAiService;
        this.travelDiaryGraphMapper = travelDiaryGraphMapper;
        this.travelGraphMapper = travelGraphMapper;
    }

    public PostDetailResponse regist(PostRegistRequest request) {
        Post post = new Post();
        post.setContent(request.getContent());
        post.setUserId(request.getUserId());
        post.setPostImage(request.getPostImage());
        post.setTripId(request.getTripId());
        mapper.regist(post);

        try {
            String result = openAiService.analyzeText(request.getContent());
            TagWeights tagWeights = new TagWeights();
            tagWeights.setUserId(post.getUserId());

            result = result.substring(1, result.length() - 1);

            String[] pairs = result.split(",\\s*");

            for (String pair : pairs) {
                String[] keyValue = pair.split(":\\s*");
                String key = keyValue[0];
                int value = Integer.parseInt(keyValue[1]);

                switch (key) {
                    case "sea":
                        tagWeights.setSea(value);
                        break;
                    case "mountain":
                        tagWeights.setMountain(value);
                        break;
                    case "valley":
                        tagWeights.setValley(value);
                        break;
                    case "city":
                        tagWeights.setCity(value);
                        break;
                    case "festival":
                        tagWeights.setFestival(value);
                        break;
                }
            }
            travelGraphMapper.updateTravelGraph(tagWeights); // 유저에 그래프 수치 저장
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PostDetailResponse response = new PostDetailResponse();
        response.setContent(post.getContent());
        response.setId(post.getTripId());
        response.setUserId(post.getUserId());
        String facilityName = tripMapper.findFacilityNameById(post.getTripId());
        response.setFacilityName(facilityName);
        response.setCreatedAt(LocalDateTime.now());
        response.setTripId(post.getTripId());
        response.setPostImage(post.getPostImage());
        return response;
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

    public List<PostListResponse> getListByTripIdOrderByLikes(Long tripId) {
        return mapper.getListByTripIdOrderByLikes(tripId);
    }

    public List<PostListResponse> getListByTripIdOrderByLikesOnlyThree(Long tripId) {
        return mapper.getListByTripIdOrderByLikesOnlyThree(tripId);
    }

    public PostDetailResponse detail(Long postId) {
        PostDetailResponse response = mapper.selectById(postId);
        List<CommentListResponse> commentListResponse = commentMapper.selectByPostIdIncludeUserName(postId);
        response.setComments(commentListResponse);

        return response;
    }

    public void update(PostUpdateRequest request) {
        mapper.update(request);
    }

    public void delete(Long postId) {
        postHashtagMapper.deleteByPostId(postId);
        mapper.deleteByPostId(postId);
    }

    public void incrementView(Long postId, Long userId) {
        mapper.incrementView(postId, userId);
    }

    public void incrementLikes(Long postId, Long userId) {
        if (mapper.isLiked(postId, userId) == 1) {
            mapper.decrementLikes(postId, userId);
        } else {
            mapper.incrementLikes(postId, userId);
        }
    }

    public List<PostLocationResponseDto> getPostLocation(Long userId) {
        return mapper.getPostLocation(userId);
    }
}
