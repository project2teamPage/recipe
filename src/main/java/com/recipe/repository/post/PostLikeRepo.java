package com.recipe.repository.post;

import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostLike;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike, Long> {

    int countByPostId(Long postId);

    boolean existsByPostAndUser(Post post, User user);

    long countByPost(Post post);
}
