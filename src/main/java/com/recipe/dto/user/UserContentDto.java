package com.recipe.dto.user;

import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostComment;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserContentDto {

    private Long id;
    private String type;
    private String title; // 커뮤니티, 레시피 게시글의 제목
    private String content; // 댓글 내용
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;

    public static UserContentDto fromPost(Post post){
        UserContentDto dto = new UserContentDto();
        dto.setId(post.getId());
        dto.setType("커뮤니티");
        dto.setTitle(post.getTitle());
        if(post.getUploadDate() != null){
            dto.setUploadDate(post.getUploadDate());
        } else {
            dto.setUpdateDate(post.getUpdateDate());
        }

        return dto;
    }

    public static UserContentDto fromRecipe(Recipe recipe){
        UserContentDto dto = new UserContentDto();
        dto.setId(recipe.getId());
        dto.setType("레시피");
        dto.setTitle(recipe.getTitle());
        if(recipe.getUploadDate() != null){
            dto.setUploadDate(recipe.getUploadDate());
        } else {
            dto.setUpdateDate(recipe.getUpdateDate());
        }

        return dto;
    }

    public static UserContentDto fromRecipeComment(RecipeComment recipeComment){
        UserContentDto dto = new UserContentDto();
        dto.setId(recipeComment.getRecipe().getId());
        dto.setType("레시피");
        dto.setContent(recipeComment.getContent());
        if(recipeComment.getUploadDate() != null){
            dto.setUploadDate(recipeComment.getUploadDate());
        } else {
            dto.setUpdateDate(recipeComment.getUpdateDate());
        }

        return dto;
    }

    public static UserContentDto fromPostComment(PostComment postComment){
        UserContentDto dto = new UserContentDto();
        dto.setId(postComment.getPost().getId());
        dto.setType("커뮤니티");
        dto.setContent(postComment.getContent());
        if(postComment.getUploadDate() != null){
            dto.setUploadDate(postComment.getUploadDate());
        } else {
            dto.setUpdateDate(postComment.getUpdateDate());
        }

        return dto;
    }
}
