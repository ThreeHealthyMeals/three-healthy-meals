package com.example.threehealthymeals.api;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.food.FoodRepository;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.MenuRepository;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import com.example.threehealthymeals.web.dto.restaurant.RestaurantCreateRequest;
import com.example.threehealthymeals.web.dto.restaurant.RestaurantSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@RestController
public class RestaurantApi {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RestaurantCreateRequest request){
        Restaurant restaurant = restaurantRepository.save(request.toEntity());
        return ResponseEntity.ok(restaurant.getId());
    }

    @PostMapping("/_bulk")
    public ResponseEntity<?> saveLists(@RequestBody List<RestaurantCreateRequest> requests){
        List<Restaurant> restaurants = requests.stream()
                .map(RestaurantCreateRequest::toEntity)
                .collect(toList());
        List<Long> ids = restaurantRepository.saveAll(restaurants)
                .stream().map(Restaurant::getId)
                .collect(toList());
        return ResponseEntity.ok(ids);
    }

    @GetMapping
    public ResponseEntity<?> getList(){
        List<RestaurantSimple> restaurants = restaurantRepository.findAll().stream()
                .map(RestaurantSimple::new)
                .collect(toList());
        return ResponseEntity.ok(restaurants);
    }
<<<<<<< HEAD
=======

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        restaurant.ifPresent(res -> {
            Menu menu = menuRepository.getByRestaurant(res);
            if(menu.getFood() != null){
                foodRepository.findById(menu.getFood().getId()).ifPresent(foodRepository::delete);
            }
            menuRepository.delete(menu);
            restaurantRepository.delete(res);
        });
        return ResponseEntity.ok(restaurant.isPresent() ? "deleted" : "can't find");
    }
>>>>>>> develop
}
