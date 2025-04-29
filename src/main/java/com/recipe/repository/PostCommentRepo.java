package com.recipe.repository;

import com.recipe.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepo extends JpaRepository<PostComment, Long> {
}
