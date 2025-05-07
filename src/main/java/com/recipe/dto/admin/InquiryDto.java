package com.recipe.dto.admin;

import com.recipe.entity.admin.Inquiry;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class InquiryDto {
    private Long id;
    private String content;
    private String NickName;
    private LocalDateTime writeDate;

    public static InquiryDto from(Inquiry inquiry, String NickName) {
        InquiryDto inquiryDto = new InquiryDto();

        inquiryDto.setNickName(inquiry.getUser().getNickName());
        inquiryDto.setContent(inquiry.getContent());
        inquiryDto.setWriteDate(inquiry.getWriteDate());

        return inquiryDto;
    }
}
