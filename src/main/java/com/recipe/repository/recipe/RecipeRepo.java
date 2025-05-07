package com.recipe.repository.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    // RecipeId 로 레시피 찾기
    public Recipe findByIdAndIsDeletedFalse(Long id);

    // 레시피 최신순으로 목록 가져오기
    public Page<Recipe> findAllByOrderByUploadDateDesc(Pageable pageable);

    // 레시피 카테고리별 목록 가져오기
    @Query("SELECT r FROM Recipe r " +
            "WHERE (:dishType IS NULL OR r.dishType = :dishType) " +
            "AND (:theme IS NULL OR r.theme = :theme) " +
            "AND (:spicy IS NULL OR r.spicy = :spicy) " +
            "ORDER BY r.uploadDate DESC")
    Page<Recipe> findByCategory(@Param("dishType") DishType dishType,
                                  @Param("theme") Theme theme,
                                  @Param("spicy") Integer spicy,
                                  Pageable pageable);


}
