package com.example.threehealthymeals.web;

import com.example.threehealthymeals.domain.food.Food;
import com.example.threehealthymeals.domain.food.FoodRepository;
import com.example.threehealthymeals.domain.restaurant.Menu;
import com.example.threehealthymeals.domain.restaurant.MenuRepository;
import com.example.threehealthymeals.domain.restaurant.Restaurant;
import com.example.threehealthymeals.domain.restaurant.RestaurantRepository;
import com.example.threehealthymeals.web.dto.RestaurantDetail;
import com.example.threehealthymeals.web.dto.RestaurantSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.threehealthymeals.web.RestaurantController.*;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RequestMapping(ROOT+RESTAURANT)
@Controller
public class RestaurantController {

    static final String ROOT = "/";
    static final String RESTAURANT = "restaurants";

    private final FoodRepository foodRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public String getList(
//            @RequestBody String ss,
            Model model){
        List<RestaurantSimple> restaurants = restaurantRepository.findAll().stream()
                .map(RestaurantSimple::new).collect(toList());
        model.addAttribute("restaurants", restaurants);
        return RESTAURANT;
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model, RedirectAttributes attributes){
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if(restaurant.isPresent()){
            Menu menu = menuRepository.getByRestaurant(restaurant.get());
            Food food = menu.getFood();
            RestaurantDetail response = RestaurantDetail.builder()
                    .restaurant(restaurant.get())
                    .menu(menu)
                    .food(food)
                    .build();
            model.addAttribute("restaurant", response);
        } else {
            attributes.addFlashAttribute("message", "식당 정보를 찾을 수 없습니다.");
            return "redirect:/" + RESTAURANT;
        }
        return "restaurants-detail";
    }
}
