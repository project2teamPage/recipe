package com.recipe.repository;

import com.recipe.entity.BookmarkRecipe;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRecipeRepo extends JpaRepository<BookmarkRecipe, Long> {
}
