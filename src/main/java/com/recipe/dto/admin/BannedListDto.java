package com.recipe.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BannedListDto {

    private Long id; // 정지 처리 목록 번호
    private String BannedId; // 정지당한 아이디
    private String BannedNickName; // 정지당한 닉네임

}
