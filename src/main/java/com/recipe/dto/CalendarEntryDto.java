package com.recipe.dto;

import com.recipe.constant.Stamp;
import com.recipe.entity.CalendarEntryMeal;
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
