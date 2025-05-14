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


    // entity -> dto
    // 화면 표현용
    public static CalendarEntryMealListDto from(CalendarEntryMeal calendarEntryMeal){

        CalendarEntryMealListDto dto = new CalendarEntryMealListDto();

        dto.setMealType(calendarEntryMeal.getMealType());
        dto.setCalendarEntryId(calendarEntryMeal.getCalendarEntry().getId());
        dto.setBookmarkRecipeId(calendarEntryMeal.getBookmarkRecipe().getId());

        return dto;
    }

    // dto-> entity
    // 데이터 전송용
    public static CalendarEntryMeal to(CalendarEntry calendarEntry,
                                       BookmarkRecipe bookmarkRecipe){

        CalendarEntryMeal calendarEntryMeal = new CalendarEntryMeal();

        calendarEntryMeal.setMealType(calendarEntryMeal.getMealType());
        calendarEntryMeal.setCalendarEntry(calendarEntry);
        calendarEntryMeal.setBookmarkRecipe(bookmarkRecipe);

        return calendarEntryMeal;

    }


}

