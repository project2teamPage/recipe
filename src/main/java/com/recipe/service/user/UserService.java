package com.recipe.service.user;

import com.recipe.dto.user.MainUserListDto;
import com.recipe.dto.user.MemberSignInDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<MainUserListDto> getAllUsers(){
        List<User> users = userRepo.findAll();

        List<MainUserListDto> mainUserListDtoList = new ArrayList<>();
        for(User user : users){
            MainUserListDto mainUserListDto = MainUserListDto.from(user);

            mainUserListDtoList.add(mainUserListDto);
        }

        return mainUserListDtoList;
    }

    public User saveUser(MemberSignUpDto memberSignUpDto){

        User user = memberSignUpDto.toUser();

        return userRepo.save(user);

    }
    // 로그인 시큐리티 사용

}
