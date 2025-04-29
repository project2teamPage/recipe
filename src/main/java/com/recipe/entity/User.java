package com.recipe.entity;

import com.recipe.constant.Role;
import com.recipe.dto.MemberSignUpDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter  @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id; // user 테이블 번호

    @NotEmpty
    private String loginId; // 아이디

    @NotEmpty
    private String password; // 비밀번호

    @NotEmpty
    private String email; // 이메일

    private String nickName; // 닉네임
    private int familyMember; // 구성원
    private boolean isBanned; // 정지유무
    private LocalDateTime BanTime; // 정지된 시간

    @Enumerated(EnumType.STRING)
    private Role role;
}
