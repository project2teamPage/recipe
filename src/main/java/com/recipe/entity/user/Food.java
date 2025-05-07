package com.recipe.entity.user;


import com.recipe.constant.DishType;
import com.recipe.constant.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_id", nullable = false, unique = true)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DishType dishType;

    @Enumerated(EnumType.STRING)
    private Theme theme;

    private int spicy;
}
