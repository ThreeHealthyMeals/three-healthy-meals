package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RestaurantCreateRequest {
    private String name;
    private String location;
    private String imgUrl;

    public Restaurant toEntity(){
        return Restaurant.builder()
                .name(name)
                .location(location)
                .imgUrl(imgUrl)
                .build();
    }
}
