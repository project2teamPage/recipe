package com.recipe.dto.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.RecipeDifficulty;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeIngredient;
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
public class RecipeForm {

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
    private List<RecipeIngredientDto> recipeIngredientDtoList = new ArrayList<>();
    private List<RecipeStepDto> recipeStepDtoList = new ArrayList<>();

    public static RecipeForm from(Recipe recipe, List<RecipeIngredientDto> recipeIngredientDtoList,
                                  List<RecipeStepDto> recipeStepDtoList){
        RecipeForm form = new RecipeForm();
        form.setId(recipe.getId());
        form.setUserId(recipe.getUser().getId());
        form.setTitle(recipe.getTitle());
        form.setDishType(recipe.getDishType());
        form.setTheme(recipe.getTheme());
        form.setSpicy(recipe.getSpicy());
        form.setUploadDate(recipe.getUploadDate());
        form.setRecipeTime(recipe.getRecipeTime());
        form.setRecipeDifficulty(recipe.getRecipeDifficulty());
        form.setRecipeIngredientDtoList(recipeIngredientDtoList);
        form.setRecipeStepDtoList(recipeStepDtoList);

        return form;
    }

    public Recipe toRecipe(User user){
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setTitle(this.title);
        recipe.setDishType(this.dishType);
        recipe.setTheme(this.theme);
        recipe.setSpicy(this.spicy);
        recipe.setRecipeDifficulty(this.recipeDifficulty);
        recipe.setRecipeTime(this.recipeTime);
        recipe.setUploadDate(LocalDateTime.now()); // 직접 설정
        recipe.setViewCount(0); // 초기값 설정
        recipe.setDeleted(false);

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
