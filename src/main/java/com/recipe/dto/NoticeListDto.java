package com.recipe.dto;

import com.recipe.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class NoticeListDto {
    private Long id; // 공지사항 목록 번호
    private String adminNickName; // 관리자 닉네임
    private String title; // 공지사항 제목
    private LocalDateTime writeDate; // 작성일

    public static NoticeListDto from(Notice notice, String adminNickName) {
        NoticeListDto noticeListDto = new NoticeListDto();

        noticeListDto.setAdminNickName(adminNickName);
        noticeListDto.setTitle(notice.getTitle());
        noticeListDto.setWriteDate(notice.getWriteDate());

        return noticeListDto;
    }
}
