package com.recipe.dto;

import com.recipe.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CalendarCommentUpdateDto {
    private Long calendarCommentId;
    private User user;
    private String content;
}
