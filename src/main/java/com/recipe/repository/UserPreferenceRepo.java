package com.recipe.repository;

import com.recipe.entity.User;
import com.recipe.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepo extends JpaRepository<UserPreference, Long> {

    // 식이관심사 - 메인페이지에 노출용

}
