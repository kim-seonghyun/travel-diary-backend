package com.ssafy.trip.trip.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    private Long id;

    private Long locationId;

    private Long placeId;

    private String facilityName;

    private String facilityIntroduction;

    private String phoneNumber;

    private String webPageUrl;

    private Place place;

}
