package com.recipe.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class BannedListDto {

    private Long id;                 // 정지 처리 고유 번호 (Report ID 또는 Ban ID)
    private String bannedId;         // 로그인 ID
    private String bannedNickName;   // 닉네임
    private String reason;           // 정지 사유
    private String targetUrl;        // 문제된 글/댓글 링크
    private LocalDateTime bannedDate;  // 정지 시작일
    private LocalDateTime bannedUntil; // 정지 종료일

}
