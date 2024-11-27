package com.ssafy.trip.traveldiary.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class TravelDiaryRegisterRequest {
    private String title;
    private Long locationId;
    private String description;
    private int dotoriPrice;
    private String forSale;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long userId;
    private String imageName;
    private List<Long> selectedPosts;
}

