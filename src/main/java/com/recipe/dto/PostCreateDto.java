package com.recipe.dto;

import com.recipe.constant.PostCategory;
import com.recipe.entity.PostImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PostCreateDto {

    private Long id;
    private String title;
    private String content;
    private PostCategory postCategory;

    private List<PostImageDto> postImageDtoList;

}
