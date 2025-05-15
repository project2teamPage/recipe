package com.recipe.dto.user;

import com.recipe.entity.user.CalendarComment;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.events.Comment;

import static com.recipe.dto.user.CalendarCommentDto.modelMapper;

@Getter @Setter
public class CalendarCommentListDto {
    private User userId;
    private String content;

    public static CalendarCommentListDto from(Comment calendarComment){
        return modelMapper.map(calendarComment , CalendarCommentListDto.class);
    }
}
