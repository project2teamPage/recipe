package com.recipe.dto.post;

import com.recipe.entity.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter
public class PostListDto {

    private Long id;
    private String title;
    private String nickName;
    private String imageUrl;
    private int postLikes;
    private LocalDateTime uploadDate;

    public static PostListDto from(Post post, String imageUrl, int postLikes){
        ModelMapper modelMapper = new ModelMapper();

        PostListDto dto = modelMapper.map(post, PostListDto.class);
        dto.setNickName(post.getUser().getNickName());
        dto.setImageUrl(imageUrl);
        dto.setPostLikes(postLikes);

        return dto;
    }



}
