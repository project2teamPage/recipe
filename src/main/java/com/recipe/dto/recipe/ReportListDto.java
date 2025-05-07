package com.recipe.dto.recipe;

import com.recipe.entity.admin.Report;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ReportListDto {

    private Long id; // 신고목록 번호
    private String userId; // 신고자 아이디
    private String userNickName; // 신고자 닉네임
    private Long targetId; // 피신고자 아이디
    private String targetNickName; // 피신고자 닉네임
    private String targetType; // 신고 카테고리
    private String title; // 신고 제목
    private String reason; // 신고 사유
    private LocalDateTime date; // 신고날짜

    public static ReportListDto from(Report report, String targetNickName) {
        ReportListDto reportListDto = new ReportListDto();

        reportListDto.setUserId(report.getUser().getLoginId());
        reportListDto.setUserNickName(report.getUser().getNickName());
        reportListDto.setTargetId(report.getTargetId());
        reportListDto.setTargetType(report.getTargetType().getTypeName());
        reportListDto.setTargetNickName(targetNickName);
        reportListDto.setTitle(report.getTitle());
        reportListDto.setReason(report.getReason());
        reportListDto.setDate(report.getDate());

        return reportListDto;
    }

}
