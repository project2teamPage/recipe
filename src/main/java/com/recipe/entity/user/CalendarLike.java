package com.recipe.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CalendarLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="calendar_like_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
