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




    public static RecipeStepDto from (RecipeStep recipeStep){
        RecipeStepDto dto = new RecipeStepDto();

        dto.setId(recipeStep.getId() );
        dto.setTitle(recipeStep.getTitle());
        dto.setContent(recipeStep.getContent());
        dto.setStepOrder(recipeStep.getStepOrder());
        dto.setImgName(recipeStep.getImgName());
        dto.setImgOriginalName(recipeStep.getImgOriginalName());
        dto.setImgUrl(recipeStep.getImgUrl());

        return dto;
    }

    public RecipeStep to(Recipe recipe){
        RecipeStep step = new RecipeStep();
        step.setTitle(this.title);
        step.setContent(this.content);
        step.setStepOrder(this.stepOrder);
        step.setImgName(this.imgName);
        step.setImgOriginalName(this.imgOriginalName);
        step.setImgUrl(this.imgUrl);
        step.setRecipe(recipe);

        return step;
    }


}
