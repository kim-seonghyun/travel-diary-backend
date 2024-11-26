package com.ssafy.trip.traveldiary.dto.response;

import com.ssafy.trip.post.dto.response.PostListResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class TravelDiaryDetailResponse {
    private long id;
    private long userId;
    private String title;
    private String description;
    private String imageName;
    private String username;
    private int dotoriPrice;
    private LocalDateTime createdAt;
    private List<PostListResponse> posts;
    private boolean isPurchased;
}
