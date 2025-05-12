package com.recipe.repository.user;

import com.recipe.constant.Role;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByRole(Role role);

    Optional<User> findByLoginId(String loginId);
}
