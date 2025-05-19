package com.recipe.entity.admin;

import com.recipe.constant.TargetType;
import com.recipe.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id; // 신고 테이블 번호

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 신고자 아이디

    @Enumerated(EnumType.STRING)
    private TargetType targetType; // 신고 카테고리(레시피게시글, 레시피댓글, 커뮤니티게시글, 커뮤니티댓글)

    private Long targetId; // 피신고된 글/댓글 아이디

    private String targetNickName; // 피신고자 닉네임

    private String title; // 신고제목

    @Column(columnDefinition = "TEXT")
    private String reason; // 신고사유

    private LocalDateTime date; // 신고날짜

    private LocalDateTime bannedDate; // 정지날짜
    
    private String bannedReason; // 정지 사유



}
