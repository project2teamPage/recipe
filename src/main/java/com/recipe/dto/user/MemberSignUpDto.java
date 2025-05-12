package com.recipe.dto.user;

import com.recipe.constant.Role;
import com.recipe.constant.Theme;
import com.recipe.entity.user.Food;
import com.recipe.entity.user.User;
import com.recipe.entity.user.UserFavorite;
import com.recipe.entity.user.UserPreference;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemberSignUpDto {
    @NotEmpty(message = "아이디를 입력해주세요.")
    @Length(min = 5, max = 15, message = "영어 소문자, 숫자 5~15자리로 입력해주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Length(min = 10, max = 16, message = "형식에 따라 작성해주세요.")
    private String password;

    @Email(message = "이메일 형식에 맞춰 작성해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Length(min = 2, max = 8, message = "최소 2글자, 최대 8글자 입력 가능합니다.")
    private String nickName;

    private List<Theme> themeList;
    private List<Food> foodList;
    private int familyMember;

    public static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.typeMap(MemberSignUpDto.class, User.class)
                .addMappings(mapper ->{
                        mapper.skip(User::setId);}
               );
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = modelMapper.map(this, User.class);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));

        return user;
    }

    // 식이관심사
    public List<UserPreference> toPreference(User user) {

        List<Theme> themeList = this.getThemeList();
        List<UserPreference> userPreferenceList = new ArrayList<>();

        for (Theme theme : themeList) {
            UserPreference userPreference = new UserPreference();
            userPreference.setUser(user);
            userPreference.setTheme(theme);

            userPreferenceList.add(userPreference);
        }
        return userPreferenceList;
    }

    // 음식 호불호
    public List<UserFavorite> toFavorite(User user) {

        List<Food> foodList = this.getFoodList();
        List<UserFavorite> userFavoriteList = new ArrayList<>();

        for (Food food : foodList) {
            UserFavorite userFavorite = new UserFavorite();
            userFavorite.setUser(user);
            userFavorite.setFood(food);

            userFavoriteList.add(userFavorite);
        }

        return userFavoriteList;
    }
//    @AssertTrue(message = "만 14세 이상 이용 가능합니다.")
//    private boolean agreeAge;
//
//    @AssertTrue(message = "이용약관에 동의해야 합니다.")
//    private boolean agreeTerms;
//
//    @AssertTrue(message = "개인정보 수집에 동의해야 합니다.")
//    private boolean agreePrivacy;
}
