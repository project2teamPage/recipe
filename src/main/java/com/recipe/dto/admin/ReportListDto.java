package com.recipe.dto.admin;

import com.recipe.entity.admin.Report;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter

public class ReportListDto {

    private Long id; // 신고목록 번호
    private String userId; // 신고자 아이디
    private String userNickName; // 신고자 닉네임
    private String targetNickName; // 피신고자 닉네임
    private String targetLoginId; // 피신고자 아이디
    private String targetType; // 신고 카테고리
    private String title; // 신고 제목
    private String reason; // 신고 사유
    private String date; // 신고날짜
    private String targetUrl; // 신고된 글/댓글로 이동하는 링크

    public static ReportListDto from(Report report, String targetLoginId, String targetNickName, String url) {
        ReportListDto reportListDto = new ReportListDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 신고자 정보
        reportListDto.setUserId(report.getUser().getLoginId());
        reportListDto.setUserNickName(report.getUser().getNickName());

        // 피신고자 정보
        reportListDto.setTargetLoginId(targetLoginId);
        reportListDto.setTargetNickName(targetNickName);

        // 기타 신고 정보
        reportListDto.setTargetType(report.getTargetType().getTypeName());
        reportListDto.setTitle(report.getTitle());
        reportListDto.setReason(report.getReason());
        reportListDto.setDate(report.getDate().format(formatter));
        reportListDto.setTargetUrl(url);

        return reportListDto;

    }

}
