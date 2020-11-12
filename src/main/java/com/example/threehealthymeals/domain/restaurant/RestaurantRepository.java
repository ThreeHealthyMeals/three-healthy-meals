package com.example.threehealthymeals.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findFirstByName(String name);
}
