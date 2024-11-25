package com.ssafy.trip.traveldiary.dto.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class TravelDiary {
    private long id;
    private long userId;
    private long locationId;
    private String field;
    private String title;
    private String description;
    private String imageName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String forSale;
    private Integer dotoriPrice;
}
