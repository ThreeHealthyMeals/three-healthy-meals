package com.example.threehealthymeals.domain.restaurant;

import com.example.threehealthymeals.domain.BaseTimeEntity;
import com.example.threehealthymeals.domain.food.Food;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @NotNull
    private String name;

    @NotNull
    private int price;

    private String description;

    private String imgUrl;

    @Builder
    public Menu(Restaurant restaurant, Food food, String name,
                int price, String description, String imgUrl){
        this.restaurant = restaurant;
        this.food = food;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public void updateImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }
}
