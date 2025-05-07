package com.recipe.dto.user;

import com.recipe.entity.user.CalendarComment;
import com.recipe.entity.user.CalendarEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CalendarDto {
    private List<CalendarComment> calendarCommentList;
    private List<CalendarEntry> calendarEntryList;
    private int calendarLike;
}
