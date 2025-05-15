package com.recipe.repository.user;

import com.recipe.entity.user.BookmarkRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookmarkRecipeRepo extends JpaRepository<BookmarkRecipe, Long> {

    // 사용자가 즐겨찾기 할 때
    List<BookmarkRecipe> findAllByUserId(Long userId);

    // 사용자가 즐겨찾기를 해제했을 떼
    public void deleteByUserIdAndRecipeId(Long userId, Long recipeId);


}
