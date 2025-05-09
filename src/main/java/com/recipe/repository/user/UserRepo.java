package com.recipe.repository.user;

import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    // 로그인할 때 메서드
    public boolean findByLoginIdAndPassword(String loginId, String password);

    Optional<User> findByLoginId(String loginId);
}
