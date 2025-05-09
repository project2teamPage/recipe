package com.recipe.dto.recipe;

import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeIngredient;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class RecipeIngredientDto {
    private Long id;
    private String name;
    private String amount;

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
