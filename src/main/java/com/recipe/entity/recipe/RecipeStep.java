package com.recipe.entity.recipe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_step_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private String title;
    private String content;
    private int stepOrder;
    private String imgName;
    private String imgOriginalName;
    private String imgUrl;
    private boolean isThumbnail;

}
