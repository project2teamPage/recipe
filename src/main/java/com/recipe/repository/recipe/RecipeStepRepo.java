package com.recipe.repository.recipe;

import com.recipe.entity.recipe.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeStepRepo extends JpaRepository<RecipeStep, Long> {

    // 상세페이지에 보여질 레시피 단계 목록
    public List<RecipeStep> findAllByRecipeIdOrderByStepOrder(Long recipeId);

    // 레시피 썸네일 이미지
    public RecipeStep findByRecipeIdAndIsThumbnailIsTrue(Long id);

}
