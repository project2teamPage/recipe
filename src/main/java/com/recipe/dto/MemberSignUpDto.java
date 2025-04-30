package com.recipe.dto;

import com.recipe.constant.Theme;
import com.recipe.entity.Food;
import com.recipe.entity.User;
import com.recipe.entity.UserFavorite;
import com.recipe.entity.UserPreference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemberSignUpDto {
    @NotEmpty
    @Length(min = 5, max = 15, message = "영어 소문자, 숫자 5~15자리로 입력해주세요.")
    private String loginId;

    @NotEmpty
    @Length(min = 10, max = 16, message = "영어 대소문자, 숫자 10~16자리로 입력해주세요.")
    private String password;

    @Email(message = "이메일 형식에 맞춰 작성해주세요.")
    private String email;

    @NotEmpty
    @Length(min = 2, max = 8, message = "최소 2글자, 최대 8글자 입력 가능합니다.")
    private String nickName;

    private List<Theme> themeList;
    private List<Food> foodList;
    private int familyMember;

    public static ModelMapper modelMapper = new ModelMapper();

    public User toUser(){
        User user = modelMapper.map(this, User.class);
        user.setLoginId(this.loginId);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setNickName(this.nickName);
        user.getFamilyMember(this.familyMember);

        return user;
    }

    // 식이관심사
    public List<Theme> toPreference() {
        List<Theme> themeList = new ArrayList<>();

        for (Theme theme : this.getThemeList()){
            themeList.add(  );
        }
        return themeList;
    }

    // 음식 호불호
    public List<Food> toFavorite(){
        List<Food> foodList = new ArrayList<>();

        for(Food food : this.getFoodList() ){
            foodList.add( );
        }

        return foodList;
    }
}
