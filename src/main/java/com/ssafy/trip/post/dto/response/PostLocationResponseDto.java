package com.ssafy.trip.post.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostLocationResponseDto {
    private Long id;
    private String facilityName;
    private LocalDateTime createdAt;
}
