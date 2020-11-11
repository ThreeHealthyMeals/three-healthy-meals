package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RestaurantDetail extends RestaurantSimple{

    private MenuResponse menu;
    private FoodResponse food;

    @Builder
    public RestaurantDetail(Restaurant restaurant, Menu menu, Food food){
        super(restaurant);
        this.menu = new MenuResponse(menu);
        this.food = new FoodResponse(food);
    }
}
