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

    public static ModelMapper modelMapper = new ModelMapper();

    public static RecipeCommentDto from(RecipeComment recipeComment){
        RecipeCommentDto recipeCommentDto = modelMapper.map(recipeComment, RecipeCommentDto.class);
        recipeCommentDto.setNickName(recipeComment.getUser().getNickName());

        return recipeCommentDto;
    }

    public RecipeComment to(User user, Recipe recipe){
        RecipeComment recipeComment = modelMapper.map(this, RecipeComment.class);
        recipeComment.setRecipe(recipe);
        recipeComment.setUser(user);

        return recipeComment;
    }




}
