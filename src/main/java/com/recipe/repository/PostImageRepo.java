package com.recipe.repository;

import com.recipe.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepo extends JpaRepository<PostImage, Long> {



}
