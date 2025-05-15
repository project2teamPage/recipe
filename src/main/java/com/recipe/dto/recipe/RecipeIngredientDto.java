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

    public static RecipeIngredientDto from (RecipeIngredient recipeIngredient){
        RecipeIngredientDto dto = new RecipeIngredientDto();
        dto.setId(recipeIngredient.getId() );
        dto.setName(recipeIngredient.getName() );
        dto.setAmount(recipeIngredient.getAmount());

        return dto;
    }

    public RecipeIngredient to (Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setName(this.getName());
        recipeIngredient.setAmount(this.getAmount());

        return  recipeIngredient;
    }


}
