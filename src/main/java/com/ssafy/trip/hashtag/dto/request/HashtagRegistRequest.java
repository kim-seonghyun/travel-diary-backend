package com.ssafy.trip.hashtag.dto.request;

import lombok.Data;

@Data
public class HashtagRegistRequest {
    private Long postId;
    private String tag;
}
