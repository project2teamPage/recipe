package com.recipe.repository;

import com.recipe.entity.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeStepRepo extends JpaRepository<RecipeStep, Long> {



}
