package com.recipe.repository;

import com.recipe.entity.Recipe;
import com.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepo extends JpaRepository<RecipeIngredient, Long> {

    
    // 레시피의 재료 목록
    public List<RecipeIngredient> findAllByRecipeId(Long recipeId);

    // 냉장고 재료 검색
    @Query("SELECT DISTINCT ri.recipe FROM RecipeIngredient ri WHERE ri.name IN :names")
    public List<Recipe> selectFridgeIngredient(@Param("names") List<String> names);



}
