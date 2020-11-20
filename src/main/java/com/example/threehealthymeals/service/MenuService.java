package com.example.threehealthymeals.service;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.food.FoodRepository;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.MenuRepository;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import com.example.threehealthymeals.web.dto.MenuCreateRequest;
import com.example.threehealthymeals.web.dto.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;

    public MenuResponse create(MenuCreateRequest request){
        return restaurantRepository.findById(request.getRestaurantId()).map(restaurant -> {
            Food food = foodRepository.findByName(request.getFood().getName())
                    .orElseGet(() -> foodRepository.save(request.getFood().toEntity()));
            Menu menu = menuRepository.save(request.toEntity(restaurant, food));
            return new MenuResponse(menu);
        }).orElse(null);
    }
}
