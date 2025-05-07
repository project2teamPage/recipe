package com.recipe.repository.post;

import com.recipe.entity.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepo extends JpaRepository<PostImage, Long> {

    public PostImage findFirstByPostIdAndIsThumbnailTrue(Long postId);

    public List<PostImage> findByPostId(Long postId);

}
