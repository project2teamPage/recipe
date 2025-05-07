package com.recipe.dto.user;

import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CalendarCommentUpdateDto {
    private Long calendarCommentId;
    private User user;
    private String content;
}
