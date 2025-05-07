package com.recipe.repository.user;

import com.recipe.entity.user.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {

    // 음식 (호불호) -
}
