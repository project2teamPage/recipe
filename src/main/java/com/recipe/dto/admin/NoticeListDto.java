package com.recipe.dto.admin;

import com.recipe.entity.admin.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter

public class NoticeListDto {
    private Long id; // 공지사항 목록 번호
    private String adminNickName; // 관리자 닉네임
    private String title; // 공지사항 제목
    private LocalDateTime writeDate; // 작성일
    private LocalDateTime updateDate; // 공지사항 수정날짜
    private Boolean pinned; // 공지사항 게시글 고정 여부
    private Boolean hidden; // 공지사항 게시글 숨김 여부

    public static NoticeListDto from(Notice notice, String adminNickName) {
        NoticeListDto noticeListDto = new NoticeListDto();
        noticeListDto.setId(notice.getId());
        noticeListDto.setAdminNickName(adminNickName);
        noticeListDto.setTitle(notice.getTitle());
        noticeListDto.setWriteDate(notice.getWriteDate());
        noticeListDto.setUpdateDate(notice.getUpdateDate());
        noticeListDto.setPinned(notice.isPinned());
        noticeListDto.setHidden(notice.isHidden());

        return noticeListDto;
    }

    public boolean isPinned() {
        return pinned;
    }

    public boolean isHidden() {
        return hidden;
    }

    // ✅ 추가: 포맷된 날짜 문자열 반환
    public String getFormattedWriteDate() {
        LocalDateTime dateToFormat = (updateDate != null) ? updateDate : writeDate;
        return dateToFormat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
