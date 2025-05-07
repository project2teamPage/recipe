package com.recipe.dto.user;

import com.recipe.entity.user.Calendar;
import com.recipe.entity.user.CalendarComment;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Setter @Getter
public class CalendarCommentDto {
    private Long id;
    private Long calendarId; //
    private Long userId;     //
    private String content;
    private String nickName; //
    private LocalDateTime writeDate;
    // writeDate 는 수정되면 업데이트 안한다
    private boolean isUpdated;

    public static ModelMapper modelMapper = new ModelMapper();

    // dto->entity로 보낼 때
    public CalendarComment to(Calendar calendar, User user){

        CalendarComment calendarComment =
                modelMapper.map(this, CalendarComment.class);

        calendarComment.setCalendar(calendar);
        calendarComment.setUser(user);

        return calendarComment;
    }
    // entity->dto로 보낼 때
    public static CalendarCommentDto from(CalendarComment calendarComment){

        CalendarCommentDto calendarCommentDto = modelMapper.map(calendarComment, CalendarCommentDto.class);

        return null;
    }



}
