package com.example.threehealthymeals.web.dto.restaurant;

import com.example.threehealthymeals.domain.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RestaurantCreateRequest {

    private String name;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private boolean coronaSafe;

    public Restaurant toEntity(){
        return Restaurant.builder()
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .latitude(latitude)
                .longitude(longitude)
                .coronaSafe(coronaSafe)
                .build();
    }
}
