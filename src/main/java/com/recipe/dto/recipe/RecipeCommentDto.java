package com.recipe.dto.recipe;

import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeComment;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter
public class RecipeCommentDto {
    private Long id;
    private String nickName;
    private String content;
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;


    public static RecipeCommentDto from(RecipeComment recipeComment){
        RecipeCommentDto dto = new RecipeCommentDto();

        dto.setId(recipeComment.getId());
        dto.setNickName(recipeComment.getUser().getNickName());
        dto.setContent(recipeComment.getContent());
        dto.setUploadDate(recipeComment.getUploadDate());
        dto.setUpdateDate(recipeComment.getUpdateDate());

        return dto;
    }

    public RecipeComment to(User user, Recipe recipe){
        RecipeComment recipeComment = new RecipeComment();
        recipeComment.setContent(this.content);
        recipeComment.setRecipe(recipe);
        recipeComment.setUser(user);
        recipeComment.setUpdateDate(this.getUpdateDate());
        recipeComment.setUploadDate(this.getUploadDate());

        return recipeComment;
    }




}
