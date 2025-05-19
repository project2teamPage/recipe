package com.recipe.repository.post;

import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostLike;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {

    int countByPostId(Long postId);

    boolean existsByPostAndUser(Post post, User user);

    long countByPost(Post post);


    @Modifying
    @Transactional
    @Query("DELETE FROM PostLike pl WHERE pl.post = :post AND pl.user = :user")
    void deleteByPostAndUser(Post post, User user);
}
