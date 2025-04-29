package com.recipe.dto;

import com.recipe.entity.CalendarComment;
import com.recipe.entity.CalendarEntry;
import com.recipe.entity.CalendarEntryMeal;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.ls.LSException;

import java.util.List;

@Getter @Setter
public class CalendarDto {
    private List<CalendarComment> calendarCommentList;
    private List<CalendarEntry> calendarEntryList;
    private int calendarLike;
}
