package com.recipe.repository;

import com.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    public Recipe findByIdAndIsDeletedFalse(Long id);

}
