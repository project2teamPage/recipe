package com.recipe.repository.recipe;

import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeLike;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeLikeRepo extends JpaRepository<RecipeLike, Long> {

    
    // 레시피 좋아요 수
    public int countByRecipeId(Long recipeId);

    // 좋아요 눌럿는지 확인용
    boolean existsByRecipeAndUser(Recipe recipe, User user);

    // 좋아요 삭제용
    void deleteByRecipeAndUser(Recipe recipe, User user);
}
