package com.recipe.dto.admin;

import com.recipe.entity.admin.Notice;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter

public class NoticeDto {
    private Long id;
    private String adminNickName; // 상세페이지 조회용
    private String title; // 작성 + 조회
    private String content; // 작성 + 조회
    private LocalDateTime writeDate; // 상세페이지 조회용

    public static NoticeDto from (Notice notice, String adminNickName) {
        NoticeDto noticeDto = new NoticeDto();

        noticeDto.setId(notice.getId());
        noticeDto.setAdminNickName(adminNickName);
        noticeDto.setTitle(notice.getTitle());
        noticeDto.setContent(notice.getContent());
        noticeDto.setWriteDate(notice.getWriteDate());

        return noticeDto;
    }

}
