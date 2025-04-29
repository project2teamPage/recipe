package com.recipe.dto;

import com.recipe.constant.Theme;
import com.recipe.entity.Food;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MemberSignUpDto {
    private String loginId;
    private String password;
    private String email;
    private String nickName;
    private List<Theme> themeList;
    private List<Food> foodList;
    private int familyMember;

}
