package com.recipe.dto;

import com.recipe.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CalendarCommentCreateDto {
    private Long calendarId;
    private User user;
    private String content;
}
