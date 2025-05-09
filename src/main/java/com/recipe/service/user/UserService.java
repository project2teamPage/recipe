package com.recipe.service.user;

import com.recipe.dto.user.MainUserListDto;
import com.recipe.dto.user.MemberSignInDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepo userRepo;

    // 회원가입 정보 저장
    public User saveUser(MemberSignUpDto memberSignUpDto, PasswordEncoder passwordEncoder) {

        User user = memberSignUpDto.toUser(passwordEncoder);
        ValidUserId(user);

        return userRepo.save(user);

    }
    // 로그인 시큐리티 사용

    // 아이디 중복 체크
    private void ValidUserId(User user) {
        User find = userRepo.findByLoginId(user.getLoginId());

        // 찾은 아이디가 null값이 아니면(아이디 일치)
        if (find != null) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
    }



}
