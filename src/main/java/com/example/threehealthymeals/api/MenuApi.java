package com.example.threehealthymeals.api;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.food.FoodRepository;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.MenuRepository;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import com.example.threehealthymeals.web.dto.MenuCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/menus")
@RestController
public class MenuApi {

    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MenuCreateRequest request){
        Restaurant restaurant = restaurantRepository.findFirstByName(request.getRestaurant());
        Food food = foodRepository.findFirstByName(request.getFood());
        if(restaurant == null || food == null){
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
        Menu menu = menuRepository.save(request.toEntity(restaurant, food));
        return ResponseEntity.ok(menu);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateUrl(@PathVariable Long id, @RequestBody String imgUrl){
        Optional<Menu> byId = menuRepository.findById(id);
        if(!byId.isPresent()){
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
        Menu menu = byId.get();
        menu.updateImgUrl(imgUrl);
        return ResponseEntity.ok(menu);
    }
}
