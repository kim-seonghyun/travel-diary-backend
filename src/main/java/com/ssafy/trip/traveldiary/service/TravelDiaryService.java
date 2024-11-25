package com.ssafy.trip.traveldiary.service;

import com.ssafy.trip.post.mapper.PostMapper;
import com.ssafy.trip.traveldiary.dto.entity.TravelDiary;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegisterRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;
import com.ssafy.trip.traveldiary.mapper.TravelDiaryMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TravelDiaryService {
    private final TravelDiaryMapper mapper;
    private final PostMapper postMapper;

    public TravelDiaryService(TravelDiaryMapper mapper, PostMapper postMapper) {
        this.mapper = mapper;
        this.postMapper = postMapper;
    }


    public List<TravelDiaryListResponse> selectAll() {
        return mapper.selectAll();

    }

    public void update(TravelDiaryUpdateRequest request) {
        mapper.update(request);
    }

    public void delete(long travelDiaryId) {
        postMapper.deleteByTravelDiaryId(travelDiaryId);
        mapper.deleteById(travelDiaryId);
    }

    public TravelDiaryDetailResponse selectById(long travelDiaryId) {
        return mapper.selectById(travelDiaryId);
    }

    public void register(TravelDiaryRegisterRequest request) {
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
        mapper.insert(travelDiary);
        mapper.insertTravelDiaryPosts(travelDiary.getId(), request.getSelectedPosts());
    }
}
