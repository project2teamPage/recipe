package com.recipe.dto.user;

import com.recipe.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class MainUserListDto {

    private Long id;
    private String nickName;

    public static ModelMapper modelMapper = new ModelMapper();

    public static MainUserListDto from(User  user){
        MainUserListDto mainUserListDto = modelMapper.map( user, MainUserListDto.class);
        // MainUserListDto dto = new MainUserListDto();

        return mainUserListDto;
    }

}
