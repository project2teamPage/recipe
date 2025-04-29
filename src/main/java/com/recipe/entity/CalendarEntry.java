package com.recipe.entity;

import com.recipe.constant.Stamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class CalendarEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="calendar_entry_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Enumerated(EnumType.STRING)
    private Stamp stamp;
    private LocalDate date;
}
