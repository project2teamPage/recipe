package com.recipe.dto.recipe;

import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeStep;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class RecipeStepDto {

    private Long id;
    private String title;
    private String content;
    private int stepOrder;
    private String imgName;
    private String imgOriginalName;
    private String imgUrl;

    private MultipartFile imgFile;

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
