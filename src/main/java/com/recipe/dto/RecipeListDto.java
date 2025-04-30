package com.recipe.dto;

import com.recipe.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeListDto {

    private Long id;
    private String title;
    private String nickName;
    private String imgUrl;
    private int recipeLikes;

    public static RecipeListDto of(Recipe recipe, String imgUrl, int recipeLikes){
        RecipeListDto dto = new RecipeListDto();
        dto.setId(recipe.getId() );
        dto.setTitle(recipe.getTitle() );
        dto.setNickName(recipe.getUser().getNickName());
        dto.setRecipeLikes(recipeLikes);
        dto.setImgUrl(imgUrl);

        return dto;

    }


}
