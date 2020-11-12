package com.example.threehealthymeals.web;

import com.example.threehealthymeals.domain.food.FoodRepository;
import com.example.threehealthymeals.domain.restaurant.*;
import com.example.threehealthymeals.web.dto.RestaurantDetail;
import com.example.threehealthymeals.web.dto.RestaurantListRequest;
import com.example.threehealthymeals.web.dto.RestaurantSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static com.example.threehealthymeals.web.RestaurantController.RESTAURANT;
import static com.example.threehealthymeals.web.RestaurantController.ROOT;

@RequiredArgsConstructor
@RequestMapping(ROOT+RESTAURANT)
@Controller
public class RestaurantController {

    static final String ROOT = "/";
    static final String RESTAURANT = "restaurants";

    private final FoodRepository foodRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;

    @GetMapping
    public String getList(RestaurantListRequest request, Model model){
        List<RestaurantSimple> restaurants = restaurantQueryRepository.findAll(request);
        model.addAttribute("restaurants", restaurants);
        return RESTAURANT;
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model, RedirectAttributes attributes){
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if(restaurant.isPresent()){
            Menu menu = menuRepository.getByRestaurant(restaurant.get());
            RestaurantDetail response = RestaurantDetail.builder()
                    .restaurant(restaurant.get())
                    .menu(menu)
                    .build();
            model.addAttribute("restaurant", response);
        } else {
            attributes.addFlashAttribute("message", "식당 정보를 찾을 수 없습니다.");
            return "redirect:/" + RESTAURANT;
        }
        return "restaurants-detail";
    }
}
