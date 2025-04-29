package com.recipe.dto;

import com.recipe.constant.TargetType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ReportListDto {

    private Long id; // 신고목록 번호
    private Long userId; // 신고자 아이디
    private String userNickName; // 신고자 닉네임
    private Long targetId; // 피신고자 아이디
    private String targetNickName; // 피신고자 닉네임
    private TargetType targetType; // 신고 카테고리
    private String title; // 신고 제목
    private String reason; // 신고 사유
    private LocalDateTime date; // 신고날짜

}
