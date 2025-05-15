package com.recipe.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="calendar_id", nullable = false, unique = true)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int calendarLike;
}
