package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MenuCreateRequest {

    private String restaurant;
    private String food;
    private int price;
    private String name;
    private String description;
    private String imgUrl;

    public Menu toEntity(Restaurant restaurant, Food food){
        return Menu.builder()
                .restaurant(restaurant)
                .food(food)
                .price(price)
                .name(name)
                .description(description)
                .imgUrl(imgUrl)
                .build();
    }
}
