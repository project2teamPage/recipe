package com.recipe.repository.user;

import com.recipe.constant.Role;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByRole(Role role);
}
