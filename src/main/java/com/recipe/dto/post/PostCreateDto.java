package com.recipe.dto.post;

import com.recipe.constant.PostCategory;
import com.recipe.entity.post.Post;
import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class PostCreateDto {

    private Long userId; // 작성자 id
    private String title;
    private String content;
    private PostCategory postCategory;

    private List<MultipartFile> postImages;

    public Post to(User user){
        ModelMapper modelMapper = new ModelMapper();
        Post post = modelMapper.map(this, Post.class);
        post.setUser(user);


        return post;
    }

}
