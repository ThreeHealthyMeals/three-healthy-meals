package com.example.threehealthymeals.api;

import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import com.example.threehealthymeals.web.dto.restaurant.RestaurantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@RequestMapping("/restaurants")
@RestController
public class RestaurantApi {

    private final RestaurantRepository restaurantRepository;

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
}
