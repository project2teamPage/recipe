package com.recipe.repository.user;

import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    // 로그인할 때 메서드
    public boolean findByLoginIdAndPassword(String loginId, String password);


    
    public User findByLoginId(String id);

    public Optional<User> findByEmail(String email);


    /* 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);
}
