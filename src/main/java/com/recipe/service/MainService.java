package com.recipe.service;

import com.recipe.dto.user.MainUserListDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    @Autowired
    private UserRepo userRepo;

    public List<MainUserListDto> getAllUsers(){
        List<User> users = userRepo.findAll();

        List<MainUserListDto> mainUserListDtoList = new ArrayList<>();
        for(User user : users){
            MainUserListDto mainUserListDto = MainUserListDto.from(user);

            mainUserListDtoList.add(mainUserListDto);
        }

        List<MainUserListDto> userList = mainUserListDtoList.size() > 10 ? mainUserListDtoList.subList(0, 10) : mainUserListDtoList;

        return userList;
    }
}
