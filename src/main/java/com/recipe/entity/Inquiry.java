package com.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id; // 문의사항 테이블 번호

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 문의사항 작성자
    private String content; // 문의사항 내용
    private LocalDateTime writeDate; // 문의사항 작성일

}
