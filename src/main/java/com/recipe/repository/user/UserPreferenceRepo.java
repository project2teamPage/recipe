package com.recipe.repository.user;

import com.recipe.entity.user.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepo extends JpaRepository<UserPreference, Long> {

    // 식이관심사 -
}
