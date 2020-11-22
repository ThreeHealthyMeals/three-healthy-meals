package com.example.threehealthymeals.web.dto.restaurant;

import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.example.threehealthymeals.web.dto.food.FoodResponse;
import com.example.threehealthymeals.web.dto.menu.MenuResponse;
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
    public RestaurantDetail(Restaurant restaurant, Menu menu){
        super(restaurant);
        this.menu = new MenuResponse(menu);
        this.food = new FoodResponse(menu.getFood());
    }
}
