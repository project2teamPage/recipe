package com.recipe.dto.user;

import com.recipe.entity.user.Calendar;
import com.recipe.entity.user.CalendarComment;
import com.recipe.entity.user.CalendarEntry;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CalendarDto {
    private Long id; // 캘린더 id
    private Long userId; // 유저 id
    private String nickName;
    private List<CalendarComment> calendarCommentList;
    private List<CalendarEntry> calendarEntryList;
    private int calendarLike;


    // entity -> dto
    // 화면 출력
    public static CalendarDto from(Calendar calendar, List<CalendarComment> calendarCommentList
    , List<CalendarEntry> calendarEntryList){
        CalendarDto dto = new CalendarDto();
        dto.setId(calendar.getId());
        dto.setUserId(calendar.getUser().getId());
        dto.setNickName(calendar.getUser().getNickName());
        dto.setCalendarCommentList(calendarCommentList);
        dto.setCalendarEntryList(calendarEntryList);
        dto.setCalendarLike(calendar.getCalendarLike());

        return dto;
    }

    // dto -> entity
    // 저장용
    public Calendar to(User user){
        Calendar calendar = new Calendar();
        calendar.setUser(user);
        calendar.setCalendarLike(0);

        return calendar;

    }
}
