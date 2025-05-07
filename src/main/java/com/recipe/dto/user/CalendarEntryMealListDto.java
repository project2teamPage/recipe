package com.recipe.dto.user;

import com.recipe.constant.MealType;
import com.recipe.entity.user.BookmarkRecipe;
import com.recipe.entity.user.CalendarEntry;
import com.recipe.entity.user.CalendarEntryMeal;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class CalendarEntryMealListDto {
    private Long id;
    private Long calendarEntryId;
    private Long bookmarkRecipeId;
    private MealType mealType;

    private static ModelMapper modelMapper = new ModelMapper();

    public CalendarEntryMeal to (CalendarEntry calendarEntry,
                                 BookmarkRecipe bookmarkRecipe){

        CalendarEntryMeal calendarEntryMeal = modelMapper.map(this, CalendarEntryMeal.class);
        calendarEntryMeal.setCalendarEntry(calendarEntry);
        calendarEntryMeal.setBookmarkRecipe(bookmarkRecipe);

        return calendarEntryMeal;
    }

    public static CalendarEntryMealListDto from(CalendarEntryMeal calendarEntryMeal){

        CalendarEntryMealListDto calendarEntryMealListDto = new CalendarEntryMealListDto();
        calendarEntryMealListDto.setMealType(calendarEntryMeal.getMealType());
        calendarEntryMealListDto.setCalendarEntryId(calendarEntryMeal.getCalendarEntry().getId());
        calendarEntryMealListDto.setBookmarkRecipeId(calendarEntryMeal.getBookmarkRecipe().getId());

        return calendarEntryMealListDto;

    }
}

