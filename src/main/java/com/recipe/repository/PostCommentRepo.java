package com.recipe.repository;

import com.recipe.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepo extends JpaRepository<PostComment, Long> {

    public List<PostComment> findByPostId(Long postId);

}
