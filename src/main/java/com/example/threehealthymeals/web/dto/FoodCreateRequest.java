package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.food.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class FoodCreateRequest {

    private String name;
    private double carbohydrate;
    private double protein;
    private double fat;
    private double calorie;

    public Food toEntity(){
        return Food.builder()
                .name(name)
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .fat(fat)
                .protein(protein)
                .build();
    }
}
