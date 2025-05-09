package com.recipe.service.user;

import com.recipe.config.CustomUserDetails;
import com.recipe.dto.user.MainUserListDto;
import com.recipe.dto.user.MemberSignInDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
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
    private UserRepo userRepo;

    public User saveUser(MemberSignUpDto memberSignUpDto){

        User user = memberSignUpDto.toUser();

        return userRepo.save(user);

    }

    public List<MainUserListDto> getAllUsers(){
        List<User> users = userRepo.findAll();

        List<MainUserListDto> mainUserListDtoList = new ArrayList<>();
        for(User user : users){
            MainUserListDto mainUserListDto = MainUserListDto.from(user);

            mainUserListDtoList.add(mainUserListDto);
        }

        return mainUserListDtoList;
    }
    // 로그인 시큐리티 사용
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("로그인 실패: 아이디 없음"));

        return new CustomUserDetails(user);

    }
}
