package com.recipe.repository.post;

import com.recipe.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    public Page<Post> findByIsDeletedFalseOrderByUploadDateDesc(Pageable pageable);

}
