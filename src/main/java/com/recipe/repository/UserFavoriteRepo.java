package com.recipe.repository;

import com.recipe.entity.User;
import com.recipe.entity.UserFavorite;
import com.recipe.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteRepo extends JpaRepository<UserFavorite, Long> {

    // 호불호 -
}
