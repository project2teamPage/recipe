package com.recipe.dto;

import com.recipe.constant.TargetType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class ReportRequestDto {
    private Long targetId; // 피신고자id
    private String targetNickName; // 피신고자 닉네임

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private String title;
    private String reason;
}
