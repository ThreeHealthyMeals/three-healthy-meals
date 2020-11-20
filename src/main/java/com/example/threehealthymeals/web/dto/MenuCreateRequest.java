package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MenuCreateRequest {
    private Long restaurantId;
    private FoodCreateRequest food;
    private String name;
    private int price;
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
