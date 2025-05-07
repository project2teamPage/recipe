package com.recipe.dto.user;

import com.recipe.constant.Theme;
import com.recipe.entity.user.Food;
import com.recipe.entity.user.User;
import com.recipe.entity.user.UserFavorite;
import com.recipe.entity.user.UserPreference;
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

        return user;
    }

    // 식이관심사
    public List<UserPreference> toPreference(User user) {

        List<Theme> themeList = this.getThemeList();
        List<UserPreference> userPreferenceList = new ArrayList<>();

        for(Theme theme : themeList ){
           UserPreference  userPreference = new UserPreference();
           userPreference.setUser(user);
           userPreference.setTheme(theme);

           userPreferenceList.add(userPreference);
        }
        return userPreferenceList;
    }

    // 음식 호불호
    public List<UserFavorite> toFavorite(User user){

        List<Food> foodList = this.getFoodList();
        List<UserFavorite> userFavoriteList = new ArrayList<>();

        for( Food food : foodList ){
            UserFavorite userFavorite = new UserFavorite();
            userFavorite.setUser(user);
            userFavorite.setFood(food);

            userFavoriteList.add(userFavorite);
        }

        return userFavoriteList;
    }
}
