package com.recipe.dto.user;

import com.recipe.constant.Stamp;
import com.recipe.entity.user.CalendarEntryMeal;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class CalendarEntryDto {
    private Stamp stamp;
    private LocalDate date;
    private List<CalendarEntryMeal> calendarEntryMealList;

}
