package com.recipe.repository;

import com.recipe.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {

    public int  countByPostId(Long postId);

}
