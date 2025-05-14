package com.recipe.repository.post;

import com.recipe.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {

    public int  countByPostId(Long postId);

    boolean findByPostIdAndUserId(Long postId, Long userId);
}
