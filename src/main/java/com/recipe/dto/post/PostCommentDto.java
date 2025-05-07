package com.recipe.dto.post;

import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostComment;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter
public class PostCommentDto {

    private Long id;
    private Long postId;
    private Long userId;
    private String nickName;
    private String content;
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;

    public static ModelMapper modelMapper = new ModelMapper();

    public static PostCommentDto from(PostComment postComment){
        PostCommentDto dto = modelMapper.map(postComment, PostCommentDto.class);
        dto.setNickName(postComment.getUser().getNickName());

        return dto;
    }

    public PostComment to(Post post, User user){

        PostComment comment = modelMapper.map(this, PostComment.class);

        comment.setPost(post);
        comment.setUser(user);
        if(this.uploadDate == null){
            comment.setUploadDate(LocalDateTime.now());
        }else{
            comment.setUpdateDate(LocalDateTime.now());
        }

        return comment;
    }


}
