package com.recipe.repository.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
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
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    // RecipeId 로 레시피 찾기
    public Recipe findByIdAndIsDeletedFalse(Long id);

    List<Recipe> findAllByUser(User user);

    // 게시글 클릭 시 조회수 증가
    @Modifying
    @Query("UPDATE Recipe r SET r.viewCount = r.viewCount + 1 WHERE r.id = :id")
    void increaseViewCount(@Param("id") Long id);


    // 레시피 카테고리별 목록
    // 최신순
    @Query("SELECT r FROM Recipe r " +
            "WHERE (:dishType IS NULL OR r.dishType = :dishType) " +
            "AND (:theme IS NULL OR r.theme = :theme) " +
            "AND (:spicy IS NULL OR r.spicy = :spicy) " +
            "AND r.isDeleted = false " +
            "ORDER BY r.uploadDate DESC")
    Page<Recipe> findRecent(@Param("dishType") DishType dishType,
                                  @Param("theme") Theme theme,
                                  @Param("spicy") Integer spicy,
                                  Pageable pageable);

    // 좋아요 순
    @Query("SELECT r FROM Recipe r " +
            "WHERE (:dishType IS NULL OR r.dishType = :dishType) " +
            "AND (:theme IS NULL OR r.theme = :theme) " +
            "AND (:spicy IS NULL OR r.spicy = :spicy) " +
            "AND r.isDeleted = false " +
            "ORDER BY (SELECT COUNT(l) FROM RecipeLike l WHERE l.recipe = r) DESC")
    Page<Recipe> findByLikes(@Param("dishType") DishType dishType,
                             @Param("theme") Theme theme,
                             @Param("spicy") Integer spicy,
                             Pageable pageable);

    // 조회순
    @Query("SELECT r FROM Recipe r " +
            "WHERE (:dishType IS NULL OR r.dishType = :dishType) " +
            "AND (:theme IS NULL OR r.theme = :theme) " +
            "AND (:spicy IS NULL OR r.spicy = :spicy) " +
            "AND r.isDeleted = false " +
            "ORDER BY r.viewCount DESC")
    Page<Recipe> findByViews(@Param("dishType") DishType dishType,
                             @Param("theme") Theme theme,
                             @Param("spicy") Integer spicy,
                             Pageable pageable);


    // 삭제일이 될 때 삭제
    List<Recipe> findAllByIsDeletedTrueAndDeletedDateBefore(LocalDateTime now);


    // 메인페이지용 좋아요 수 상위3개
    @Query("SELECT r FROM Recipe r " +
            "WHERE r.isDeleted = false " +
            "ORDER BY (SELECT COUNT(l) FROM RecipeLike l WHERE l.recipe = r ) DESC")
    List<Recipe> findTop3OrderByLikes(Pageable pageable);

    
    // 작성한 user의 id 로 찾기
    List<Recipe> findAllByUserIdAndIsDeletedFalse(Long id);
}
