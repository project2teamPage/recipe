package com.recipe.dto.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.RecipeDifficulty;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeIngredient;
import com.recipe.entity.recipe.RecipeStep;
import com.recipe.entity.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RecipeCreateDto {

    private Long id;

    private Long userId;

    @NotEmpty(message = "제목을 작성하세요.")
    private String title;
    @NotNull(message = "카테고리를 선택하세요.")
    private DishType dishType;
    @NotNull(message = "테마를 선택하세요.")
    private Theme theme;
    private int spicy;
    private LocalDateTime uploadDate;
    private int recipeTime;
    @NotNull(message = "난이도를 고르세요.")
    private RecipeDifficulty recipeDifficulty;
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





}
