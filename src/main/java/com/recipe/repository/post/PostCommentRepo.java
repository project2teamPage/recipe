package com.recipe.repository.post;

import com.recipe.entity.post.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepo extends JpaRepository<PostComment, Long> {

    public List<PostComment> findByPostId(Long postId);

}
