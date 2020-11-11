package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RestaurantSimple {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String longitude;
    private String latitude;

    public RestaurantSimple(Restaurant restaurant){
        id = restaurant.getId();
        name = restaurant.getName();
        address = restaurant.getAddress();
        phoneNumber = restaurant.getPhoneNumber();
        longitude = restaurant.getLongitude();
        latitude = restaurant.getLatitude();
    }
}
