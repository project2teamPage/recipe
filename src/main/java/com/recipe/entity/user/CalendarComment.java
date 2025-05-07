package com.recipe.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Entity
@Getter @Setter
public class CalendarComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_comment_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String content;

    private LocalDateTime writeDate;

    private boolean isUpdated; // 수정됐냐 // dafault=false 새로운 데이터가 옮겨지면(수정) true

}
