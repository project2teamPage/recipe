package com.recipe.entity;

import com.recipe.constant.DishType;
import com.recipe.constant.RecipeDifficulty;
import com.recipe.constant.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Enumerated(EnumType.STRING)
    private DishType dishType;

    @Enumerated(EnumType.STRING)
    private Theme theme;

    private int spicy;

    @Enumerated(EnumType.STRING)
    private RecipeDifficulty recipeDifficulty;

    private LocalTime recipeTime;
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private boolean isDeleted;
    private LocalDateTime deletedDate;

}
