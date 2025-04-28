package com.recipe.repository;

import com.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepo extends JpaRepository<RecipeIngredient, Long> {


}
