package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.restaurant.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MenuResponse {

    private String name;
    private int price;
    private String description;
    private String imgUrl;

    public MenuResponse(Menu menu){
        name = menu.getName();
        price = menu.getPrice();
        description = menu.getDescription();
        imgUrl = menu.getImgUrl();
    }
}
