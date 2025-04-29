package com.recipe.repository;

import com.recipe.entity.Food;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {

    // 음식 (호불호) -
}
