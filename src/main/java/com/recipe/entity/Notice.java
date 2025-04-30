package com.recipe.entity;

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

    private String title; // 공지사항 제목
    private String content; // 공지사항 내용
    private LocalDateTime writeDate; // 공지사항 작성날짜
    private boolean pinned; // 공지사항 게시글 고정(0: 일반, 1: 고정)
    private boolean hidden; // 공지사항 게시글 숨김(0: 해제, 1: 숨김)
}
