package com.recipe.dto.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeIngredient;
import com.recipe.entity.recipe.RecipeStep;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RecipeCreateDto {

    private Long userId;
    private String title;
    private String content;
    private DishType dishType;
    private Theme theme;
    private int spicy;
    private LocalDateTime uploadDate;
    private List<RecipeIngredientDto> recipeIngredientDtoList;
    private List<RecipeStepDto> recipeStepDtoList;

    public static ModelMapper modelMapper = new ModelMapper();

    public Recipe toRecipe(User user){
        Recipe recipe = modelMapper.map(this, Recipe.class);
        recipe.setUser(user);

        if(this.uploadDate == null) {
            recipe.setUploadDate(LocalDateTime.now());
        }
        else recipe.setUpdateDate(LocalDateTime.now());

        return recipe;
    }

    public List<RecipeIngredient> toIngredient (Recipe recipe){

        List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
        for(RecipeIngredientDto recipeIngredientDto : this.getRecipeIngredientDtoList()){
            recipeIngredientList.add( recipeIngredientDto.to(recipe));
        }

        return recipeIngredientList;
    }

    public List<RecipeStep> toStep (Recipe recipe){

        List<RecipeStep> recipeStepList = new ArrayList<>();
        for(RecipeStepDto recipeStepDto : this.getRecipeStepDtoList()){
            recipeStepList.add(recipeStepDto.to(recipe));
        }

        return recipeStepList;
    }




}
