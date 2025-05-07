package com.recipe.repository.user;

import com.recipe.entity.user.BookmarkRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRecipeRepo extends JpaRepository<BookmarkRecipe, Long> {
}
