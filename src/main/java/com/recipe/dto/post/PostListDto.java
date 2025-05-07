package com.recipe.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostListDto {

    private Long id;
    private String title;
    private String nickName;
    private String imageUrl;
    private int postLikes;
    private LocalDateTime uploadDate;



}
