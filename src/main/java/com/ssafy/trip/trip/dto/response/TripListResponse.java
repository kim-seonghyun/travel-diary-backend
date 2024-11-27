package com.ssafy.trip.trip.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripListResponse {
    private Long id;

    private String facilityName;

    private String phoneNumber;

    private String roadAddress;

    private String webPageUrl;
}
