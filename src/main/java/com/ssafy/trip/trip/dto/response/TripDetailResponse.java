package com.ssafy.trip.trip.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TripDetailResponse {

    private Long id;

    private String roadAddress;

    private String facilityName;

    private String facilityIntroduction;

    private String phoneNumber;

    private String webPageUrl;

    private String streetNumberAddress;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
