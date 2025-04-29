package com.recipe.dto;

import com.recipe.constant.PostCategory;
import com.recipe.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class PostDetailDto {

    private Long id;
    private String nickName;
    private String title;
    private String content;
    private PostCategory postCategory;
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private int postLikes;
    private List<PostCommentDto> postCommentDtoList;
    private List<PostImageDto> postImageDtoList;

    public static ModelMapper modelMapper = new ModelMapper();

    public static PostDetailDto from(Post post, int postLikes,
                                     List<PostCommentDto> postCommentDtoList, List<PostImageDto> postImageDtoList){

        PostDetailDto dto = new PostDetailDto();
        dto = modelMapper.map(post, PostDetailDto.class);
        dto.setPostLikes(postLikes);
        dto.setPostCommentDtoList(postCommentDtoList);
        dto.setPostImageDtoList(postImageDtoList);

        return dto;
    }

}
