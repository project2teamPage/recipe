package com.recipe.entity;

import com.recipe.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter  @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")



    @Enumerated(EnumType.STRING)
    private Role role;
}
