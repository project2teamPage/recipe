package com.recipe.dto;

import com.recipe.entity.Recipe;
import com.recipe.entity.RecipeStep;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class RecipeStepDto {

    private Long id;
    private String title;
    private String content;
    private int stepOrder;
    private String imgName;
    private String imgOriginalName;
    private String imgUrl;

    public static ModelMapper modelMapper = new ModelMapper();

    public static RecipeStepDto from (RecipeStep recipeStep){
        return modelMapper.map(recipeStep, RecipeStepDto.class);
    }

    public RecipeStep to(Recipe recipe){
        RecipeStep entity = modelMapper.map(this, RecipeStep.class);
        entity.setRecipe(recipe);

        return entity;
    }


}
