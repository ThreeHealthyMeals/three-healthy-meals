package com.example.threehealthymeals.web.dto.menu;

import com.example.threehealthymeals.domain.restaurant.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MenuResponse {

    private Long id;
    private String name;
    private int price;
    private String description;
    private String imgUrl;

    public MenuResponse(Menu menu){
        id = menu.getId();
        name = menu.getName() == null ? "정보 없음" : menu.getName();
        price = menu.getPrice();
        description = menu.getDescription() == null ? "정보 없음" : menu.getDescription();
        imgUrl = menu.getImgUrl() == null ? "/img/logo.PNG" : menu.getImgUrl();
    }
}
