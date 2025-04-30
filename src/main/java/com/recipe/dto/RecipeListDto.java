package com.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeListDto {

    private Long id;
    private String title;
    private String nickName;
    private String imgUrl;
    private int recipeLikes;


}
