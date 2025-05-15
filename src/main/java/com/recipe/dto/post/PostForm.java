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
public class PostForm {

    private Long id;
    private Long userId; // 작성자 id
    private String title;
    private String content;
    private PostCategory postCategory;

    public Post to(User user){
        Post post = new Post();
        post.setUser(user);
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setPostCategory(this.postCategory);


        return post;
    }

}
