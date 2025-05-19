package com.recipe.repository.recipe;

import com.recipe.entity.recipe.RecipeComment;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeCommentRepo extends JpaRepository<RecipeComment, Long> {

    public List<RecipeComment> findAllByRecipeIdOrderByUploadDateDesc(Long recipeId);

    List<RecipeComment> findAllByUser(User user);

}
