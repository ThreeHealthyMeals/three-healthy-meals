package com.example.threehealthymeals.api;

import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import com.example.threehealthymeals.web.dto.RestaurantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/restaurants")
@RestController
public class RestaurantApi {

    private final RestaurantRepository restaurantRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RestaurantCreateRequest request){
        Restaurant restaurant = restaurantRepository.save(request.toEntity());
        return ResponseEntity.ok(restaurant);
    }
}
