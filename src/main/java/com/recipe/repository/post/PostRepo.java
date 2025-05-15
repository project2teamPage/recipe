package com.recipe.repository.post;

import com.recipe.constant.PostCategory;
import com.recipe.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    // 게시글 카테고리별 리스트
    // 최신순
    Page<Post> findByPostCategoryAndIsDeletedFalseOrderByUploadDateDesc(PostCategory postCategory, Pageable pageable);

    // 좋아요순
    @Query("SELECT p FROM Post p " +
            "WHERE p.postCategory = :category AND p.isDeleted = false " +
            "ORDER BY (SELECT COUNT(l) FROM PostLike l WHERE l.post = p) DESC")
    Page<Post> findByPostCategoryAndIsDeletedFalseOrderByLikeCountDesc(@Param("category") PostCategory postCategory, Pageable pageable);

    // 조회순
    Page<Post> findByPostCategoryAndIsDeletedFalseOrderByViewCountDesc(PostCategory postCategory, Pageable pageable);

    // 게시글 클릭 시 조회수 증가
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void increaseViewCount(@Param("id") Long id);
}
