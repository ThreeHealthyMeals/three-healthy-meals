package com.example.threehealthymeals.web.dto.food;

import com.example.threehealthymeals.domain.food.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class FoodResponse {

    private String name;
    private double carbohydrate;
    private double protein;
    private double fat;

    public FoodResponse(Food food){
        name = food.getName() == null ? "정보 없음" : food.getName();
        carbohydrate = food.getCarbohydrate();
        protein = food.getProtein();
        fat = food.getFat();
    }
}
