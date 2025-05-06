package com.recipe.repository;

import com.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    // RecipeId 로 레시피 찾기
    public Recipe findByIdAndIsDeletedFalse(Long id);

    // 레시피 최신순으로 목록 가져오기
    public Page<Recipe> findAllByOrderByUploadDateDesc(Pageable pageable);

}
