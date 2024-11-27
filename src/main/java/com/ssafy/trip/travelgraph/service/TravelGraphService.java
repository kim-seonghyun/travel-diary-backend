package com.ssafy.trip.travelgraph.service;

import com.ssafy.trip.chatgpt.service.OpenAiService;
import com.ssafy.trip.post.service.PostService;
import com.ssafy.trip.traveldiary.dto.entity.TravelDiary;
import com.ssafy.trip.travelgraph.dto.response.DiaryGraphResponse;
import com.ssafy.trip.travelgraph.dto.response.TravelGraphResponse;
import com.ssafy.trip.travelgraph.entity.DiaryGraph;
import com.ssafy.trip.travelgraph.entity.TravelGraph;
import com.ssafy.trip.travelgraph.mapper.TravelGraphMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TravelGraphService {
    private TravelGraphMapper travelGraphMapper;
    private final OpenAiService openAiService;
    private final PostService postService;

    public TravelGraphService(TravelGraphMapper travelGraphMapper, OpenAiService openAiService, PostService postService) {
        this.travelGraphMapper = travelGraphMapper;
        this.openAiService = openAiService;
        this.postService = postService;
    }

    public TravelGraphResponse getTravelGraph(Long userId) {
        //validation
        TravelGraph travelGraphEntity = travelGraphMapper.findTravelGraphByUserId(userId);

        return TravelGraphResponse.builder()
                .city(travelGraphEntity.getCity())
                .sea(travelGraphEntity.getSea())
                .festival(travelGraphEntity.getFestival())
                .valley(travelGraphEntity.getValley())
                .mountain(travelGraphEntity.getMountain())
                .build();

    }

    public DiaryGraphResponse getDiaryGraph(Long diaryId) {
        //validation
        DiaryGraph diaryGraphEntity = travelGraphMapper.findDiaryGraphByDiaryId(diaryId);

        return DiaryGraphResponse.builder()
                .city(diaryGraphEntity.getCity())
                .sea(diaryGraphEntity.getSea())
                .festival(diaryGraphEntity.getFestival())
                .valley(diaryGraphEntity.getValley())
                .mountain(diaryGraphEntity.getMountain())
                .build();
    }



    public void calculateTravelDegree(Map<String, Object> map) {
        // validation
        travelGraphMapper.calculateTravelDegree(map);
    }

    public void generateGraph(Long userId) {
        //validation
        travelGraphMapper.generateTravelGraph(userId);
    }
}
