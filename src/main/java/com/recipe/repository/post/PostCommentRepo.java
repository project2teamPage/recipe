package com.recipe.repository.post;

import com.recipe.entity.post.PostComment;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepo extends JpaRepository<PostComment, Long> {

    public List<PostComment> findByPostId(Long postId);


    List<PostComment> findAllByUser(User user);

    List<PostComment> findAllByUserId(Long id);

}
