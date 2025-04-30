package com.recipe.dto;

import com.recipe.constant.MealType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CalendarEntryMealListDto {
    private MealType mealType;
    private Long bookmarkRecipeId;

}
