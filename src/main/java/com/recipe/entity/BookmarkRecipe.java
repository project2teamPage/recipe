package com.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BookmarkRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookmark_recipe_id", nullable = false, unique = true)
    private Long id;
    private Long userId;
    private Long recipeId;

}
