package com.recipe.service;

import com.recipe.dto.MainUserListDto;
import com.recipe.entity.User;
import com.recipe.repository.UserRepo;
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
}
