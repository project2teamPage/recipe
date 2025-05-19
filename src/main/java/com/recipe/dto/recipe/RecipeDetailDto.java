package com.recipe.dto.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.RecipeDifficulty;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter
public class RecipeDetailDto {

    private Long id;
    private String loginId;
    private String nickName;
    private String title;
    private String dishType;
    private String theme;
    private int spicy;
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private RecipeDifficulty recipeDifficulty;
    private int recipeTime;
    private int recipeLikes;


    private List<RecipeIngredientDto> recipeIngredientDtoList;
    private List<RecipeCommentDto> recipeCommentDtoList;
    private List<RecipeStepDto> recipeStepDtoList;

    public static ModelMapper modelMapper = new ModelMapper();

    public static RecipeDetailDto of(Recipe recipe, List<RecipeIngredientDto> recipeIngredientDtoList,
                            List<RecipeCommentDto> recipeCommentDtoList, List<RecipeStepDto> recipeStepDtoList, int recipeLikes){

        RecipeDetailDto recipeDetailDto = new RecipeDetailDto();
        recipeDetailDto = modelMapper.map(recipe, RecipeDetailDto.class);

        recipeDetailDto.setDishType(recipe.getDishType().getLabel());
        recipeDetailDto.setTheme(recipe.getTheme().getLabel());
        recipeDetailDto.setLoginId(recipe.getUser().getLoginId());
        recipeDetailDto.setNickName( recipe.getUser().getNickName() );
        recipeDetailDto.setRecipeIngredientDtoList(recipeIngredientDtoList);
        recipeDetailDto.setRecipeCommentDtoList(recipeCommentDtoList);
        recipeDetailDto.setRecipeStepDtoList(recipeStepDtoList);
        recipeDetailDto.setRecipeLikes(recipeLikes);


        return recipeDetailDto;
    }



}