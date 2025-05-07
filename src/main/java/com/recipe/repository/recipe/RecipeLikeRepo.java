package com.recipe.repository.recipe;

import com.recipe.entity.recipe.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeLikeRepo extends JpaRepository<RecipeLike, Long> {

    
    // 레시피 좋아요 수
    public int countByRecipeId(Long recipeId);

}
