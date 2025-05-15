package com.recipe.dto.user;

import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CalendarCommentCreateDto {
    private Long calendarId;
    private User user;
    private String content;
}
