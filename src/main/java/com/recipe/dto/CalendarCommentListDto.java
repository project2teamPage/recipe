package com.recipe.dto;

import com.recipe.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CalendarCommentListDto {
    private User userId;
    private String content;
}
