package com.recipe.service;

import com.recipe.dto.RecipeCreateDto;
import com.recipe.dto.RecipeDetailDto;
import com.recipe.entity.Recipe;
import com.recipe.entity.User;
import com.recipe.repository.RecipeLikeRepo;
import com.recipe.repository.RecipeRepo;
import com.recipe.repository.RecipeStepRepo;
import com.recipe.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;
    private final RecipeStepRepo recipeStepRepo;
    private final RecipeLikeRepo recipeLikeRepo;

    public Recipe saveRecipe(RecipeCreateDto recipeCreateDto){

        User user = userRepo.findById( recipeCreateDto.getUserId() ).orElseThrow();
        Recipe recipe = recipeCreateDto.toRecipe(user);

        return recipeRepo.save( recipe );
    }



}
