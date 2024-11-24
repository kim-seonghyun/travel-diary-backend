package com.ssafy.trip.post.dto.response;

import com.ssafy.trip.comment.dto.response.CommentListResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PostDetailResponse {
    private long id;
    private String content;
    private LocalDateTime createdAt;
    private Long tripId;
    private String facilityName;
    private long userId;
    private String username;
    private String postImage;
    private List<CommentListResponse> comments;
}
