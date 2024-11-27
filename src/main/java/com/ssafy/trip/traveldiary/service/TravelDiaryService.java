package com.ssafy.trip.traveldiary.service;

import com.ssafy.trip.chatgpt.service.OpenAiService;
import com.ssafy.trip.post.dto.entity.Post;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.mapper.PostMapper;
import com.ssafy.trip.post.mapper.TravelDiaryPostMapper;
import com.ssafy.trip.post.service.PostService;
import com.ssafy.trip.traveldiary.dto.entity.TravelDiary;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegisterRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;
import com.ssafy.trip.traveldiary.mapper.TravelDiaryMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.travelgraph.entity.TravelGraph;
import com.ssafy.trip.travelgraph.service.TravelGraphService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TravelDiaryService {
    private final TravelDiaryMapper mapper;
    private final PostMapper postMapper;
    private final TravelDiaryPostMapper travelDiaryPostMapper;
    private final TravelGraphService travelGraphService;
    private final PostService postService;
    private final OpenAiService openAiService;


    public TravelDiaryService(TravelDiaryMapper mapper, PostMapper postMapper,
                              TravelDiaryPostMapper travelDiaryPostMapper,
                              TravelGraphService travelGraphService,
                              PostService postService,
                              OpenAiService openAiService) {

        this.mapper = mapper;
        this.postMapper = postMapper;
        this.travelDiaryPostMapper = travelDiaryPostMapper;
        this.travelGraphService = travelGraphService;
        this.postService = postService;
        this.openAiService = openAiService;
    }


    public List<TravelDiaryListResponse> selectAll(Long userId) {
        return mapper.selectAll(userId);
    }

    public void update(TravelDiaryUpdateRequest request) {
        mapper.update(request);
    }

    public void delete(long travelDiaryId) {
        postMapper.deleteByTravelDiaryId(travelDiaryId);
        mapper.deleteById(travelDiaryId);
    }

    public TravelDiaryDetailResponse selectById(long travelDiaryId, long userId) {
        TravelDiaryDetailResponse travelDiary = mapper.selectById(travelDiaryId, userId);
        travelDiary.setPosts(travelDiaryPostMapper.getListByTravelDiaryId(travelDiaryId));
        return travelDiary;
    }

    @Transactional
    public void register(TravelDiaryRegisterRequest request) throws Exception {

        TravelDiary travelDiary = new TravelDiary();
        travelDiary.setTitle(request.getTitle());
        travelDiary.setLocationId(request.getLocationId());
        travelDiary.setDescription(request.getDescription());
        travelDiary.setDotoriPrice(request.getDotoriPrice());
        travelDiary.setForSale(request.getForSale());
        travelDiary.setCreatedAt(request.getCreatedAt());
        travelDiary.setUpdatedAt(request.getUpdatedAt());
        travelDiary.setUserId(request.getUserId());
        travelDiary.setImageName(request.getImageName());

        mapper.insert(travelDiary); // 다이어리 생성, 다이어리 id도 같이
        mapper.insertTravelDiaryPosts(travelDiary.getId(), request.getSelectedPosts()); // 다이어리 포스터 의존성 생성
        Map<String, Long> map = new HashMap<>();
        map.put("sea", 0L);
        map.put("mountain", 0L);
        map.put("valley", 0L);
        map.put("city", 0L);
        map.put("festival", 0L);
        map.put("diaryId", travelDiary.getId());

        for (Long postId : request.getSelectedPosts()) {
            PostDetailResponse response = postService.detail(postId);

            String degree = openAiService.analyzeText(response.getContent());
            degree = degree.substring(1, degree.length() - 1);

            String[] pairs = degree.split(",\\s*");

            for (String pair : pairs) {
                String[] keyValue = pair.split(":\\s*");
                String key = keyValue[0];
                int value = Integer.parseInt(keyValue[1]);
                map.put(key, map.get(key) + value);
            }
        }
        // 여행 다이어리 그래프 새롭게 등록
        mapper.saveDiaryGraph(map);

    }
}
