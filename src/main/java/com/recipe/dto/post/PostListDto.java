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
    private String content;
    private String imageUrl;
    private int postLikes;
    private LocalDateTime uploadDate;
    private int viewCount;

    public static PostListDto from(Post post, String imageUrl, int postLikes){
        PostListDto dto = new PostListDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setNickName(post.getUser().getNickName());
        dto.setContent(post.getContent());
        dto.setImageUrl(imageUrl);
        dto.setPostLikes(postLikes);
        dto.setUploadDate(post.getUploadDate());
        dto.setViewCount(post.getViewCount());

        return dto;
    }



}
