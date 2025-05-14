package com.recipe.dto.user;

import com.recipe.constant.Stamp;
import com.recipe.entity.user.Calendar;
import com.recipe.entity.user.CalendarEntry;
import com.recipe.entity.user.CalendarEntryMeal;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class CalendarEntryDto {
    private Long id;
    private Long calenderId;
    private Stamp stamp;
    private LocalDate date;
    private List<CalendarEntryMeal> calendarEntryMealList;

    // entity -> dto
    // 화면출력

    public static CalendarEntryDto from(CalendarEntry calendarEntry, List<CalendarEntryMeal> calendarEntryMealList){
        CalendarEntryDto dto = new CalendarEntryDto();

        dto.setId(calendarEntry.getId());
        dto.setCalenderId(calendarEntry.getCalendar().getId());
        dto.setStamp(calendarEntry.getStamp());
        dto.setDate(calendarEntry.getDate());
        dto.setCalendarEntryMealList(calendarEntryMealList);

        return dto;
    }

    // dto-> entitu
    // 데이터 이동용
    public static CalendarEntry to(User user){
        CalendarEntry calendarEntry = new CalendarEntry();

        calendarEntry.setCalendar(calendarEntry.getCalendar());
        calendarEntry.setStamp(calendarEntry.getStamp());
        calendarEntry.setDate(calendarEntry.getDate());

        return calendarEntry;

    }
}
