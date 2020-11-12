package com.example.threehealthymeals.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class RestaurantListRequest {

    private String keyword;         // 키워드
    private String negativeKeyword; // 제외 식단 키워드
    private boolean coronaSafe;     // 코로나 안심식당
    private String latitude;        // 위도
    private String longitude;       // 경도
    private int maxPrice;           // 가격대
    private boolean activityData;   // 내 활동량
    private boolean calorie;        // 내 섭취 칼로리
}
