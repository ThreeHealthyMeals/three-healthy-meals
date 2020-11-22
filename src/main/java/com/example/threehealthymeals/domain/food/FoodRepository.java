package com.example.threehealthymeals.domain.food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByName(String name);

    Food findFirstByName(String name);
}
