package com.recipe.repository.post;

import com.recipe.constant.PostCategory;
import com.recipe.entity.post.Post;
import com.recipe.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);

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

    // 삭제일이 될 때 삭제될 게시글 리스트
    List<Post> findAllByIsDeletedTrueAndDeletedDateBefore(LocalDateTime now);

    // 메인페이지용 요리자랑/노하우 가져오기
    @Query("SELECT p FROM Post p " +
            "WHERE p.postCategory = :category AND p.isDeleted = false ")
    List<Post> findMainPost(@Param("category") PostCategory postCategory ,Pageable pageable);

    // 작성한 user 의 id 로 찾기
    List<Post> findAllByUserIdAndIsDeletedFalse(Long id);
}
