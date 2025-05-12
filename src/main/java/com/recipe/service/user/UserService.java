package com.recipe.service.user;

import com.recipe.dto.user.MainUserListDto;
import com.recipe.dto.user.MemberSignInDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepo userRepo;

    // 회원가입 정보 저장
    public User saveUser(@Valid  MemberSignUpDto memberSignUpDto, PasswordEncoder passwordEncoder) {
System.out.println(memberSignUpDto.getEmail());
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

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        //스프링 시큐리티 사용시 커스텀 로그인 DB의 데이터로 로그인진행하기 때문에 오버라이딩

        // 로그인 시 입력한 아이디로 회원 테이블에서 정보 조회
        User user = userRepo.findByLoginId(loginId);

        if( user == null){
            throw new UsernameNotFoundException(loginId);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPassword())
                .roles(user.getRole().toString()).build();
    }


}
