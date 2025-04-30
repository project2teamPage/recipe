package com.recipe.entity;

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
    private Long id;

    private String loginId;
    private String password;
    private String nickName;
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;



    private int familyMember;
    private boolean isBanned;
    private LocalDateTime BanTime;


}
