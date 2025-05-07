package com.recipe.entity.user;

import com.recipe.constant.MealType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CalendarEntryMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_entry_meal_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_entry_id")
    private CalendarEntry calendarEntry;


    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @ManyToOne
    @JoinColumn(name = "bookmark_recipe_id")
    private BookmarkRecipe bookmarkRecipe;
}
