package com.recipe.repository;

import com.recipe.constant.Role;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByRole(Role role);
}
