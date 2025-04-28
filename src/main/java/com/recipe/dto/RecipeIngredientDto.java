package com.recipe.dto;

import com.recipe.entity.Recipe;
import com.recipe.entity.RecipeIngredient;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class RecipeIngredientDto {
    private Long id;
    private String name;
    private float quantity;

    public static ModelMapper modelMapper = new ModelMapper();

    public static RecipeIngredientDto from (RecipeIngredient recipeIngredient){
        return modelMapper.map(recipeIngredient, RecipeIngredientDto.class);
    }

    public RecipeIngredient to (Recipe recipe) {
        RecipeIngredient recipeIngredient = modelMapper.map(this, RecipeIngredient.class);
        recipeIngredient.setRecipe(recipe);

        return  recipeIngredient;
    }


}
