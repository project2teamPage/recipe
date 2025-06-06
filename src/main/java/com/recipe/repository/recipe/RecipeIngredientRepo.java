package com.recipe.repository.recipe;

import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeIngredient;
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

    // 냉장고 재료 검색
    @Query("SELECT ri.recipe FROM RecipeIngredient ri " +
            "WHERE ri.name IN :ingredients " +
            "GROUP BY ri.recipe " +
            "HAVING COUNT(DISTINCT ri.name) = :ingredientSize")
    List<Recipe> findByIngredientNames(
            @Param("ingredients") List<String> ingredients,
            @Param("ingredientSize") long ingredientSize);



    void deleteByRecipe(Recipe recipe);
}
