package com.recipe.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class UserProfileDto {

    private String nickName;
    private String password;
    private MultipartFile profileImage; // 이미지 업로드
}
