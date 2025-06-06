package com.recipe.entity.admin;

import com.recipe.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id; // 공지사항 테이블 번호

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin; // 공지사항 작성자
    private String title; // 공지사항 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 공지사항 내용
    private LocalDateTime writeDate; // 공지사항 작성날짜
    private LocalDateTime updateDate; // 공지사항 수정날짜 추가

    private boolean pinned; // 공지사항 게시글 고정(0: 일반, 1: 고정)
    private boolean hidden; // 공지사항 게시글 숨김(0: 해제, 1: 숨김)


}
