package com.recipe.dto.user;

import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CalendarCommentListDto {
    private User userId;
    private String content;
}
