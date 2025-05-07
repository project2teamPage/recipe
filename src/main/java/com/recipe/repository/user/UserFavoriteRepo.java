package com.recipe.repository.user;

import com.recipe.entity.user.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteRepo extends JpaRepository<UserFavorite, Long> {

    // 호불호 - 메인페이지에 노출용
}
