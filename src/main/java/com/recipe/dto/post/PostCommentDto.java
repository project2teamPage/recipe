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


    public static PostCommentDto from(PostComment postComment){
        PostCommentDto dto = new PostCommentDto();
        dto.setId(postComment.getId());
        dto.setPostId(postComment.getId());
        dto.setUserId(postComment.getUser().getId());
        dto.setNickName(postComment.getUser().getNickName());
        dto.setContent(postComment.getContent());
        dto.setUploadDate(postComment.getUploadDate());
        dto.setUpdateDate(postComment.getUpdateDate());

        return dto;
    }

    public PostComment to(Post post, User user){

        PostComment comment = new PostComment();

        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(this.content);
        comment.setUploadDate(this.uploadDate);
        comment.setUpdateDate(this.updateDate);

        return comment;
    }


}
