package com.recipe.entity.user;

import com.recipe.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; // 유저 테이블 id

    private String loginId; // 로그인 id
    private String password; // 로그인 패스워드
    private String nickName; // 유저 닉네임

    @Enumerated(EnumType.STRING)
    private Role role; // 일반유저 or 관리자

    private String email; // 이메일
    private int familyMember; // 가족인원
    private boolean isBanned; // 정지여부
    private LocalDateTime banTime; // 정지일


}
