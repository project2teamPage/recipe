package com.recipe.repository;

import com.recipe.entity.RecipeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeCommentRepo extends JpaRepository<RecipeComment, Long> {

    public List<RecipeComment> findAllByRecipeIdOrderByUploadDateDesc(Long recipeId);


}
