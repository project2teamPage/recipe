package com.recipe.service.admin;

import com.recipe.dto.admin.InquiryDto;
import com.recipe.entity.admin.Inquiry;
import com.recipe.repository.admin.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public List<InquiryDto> getInquirys() {

        List<InquiryDto> inquiryDtos = new ArrayList<>();

        List<Inquiry> inquiryList = inquiryRepository.findAll();

        for(Inquiry inquiry : inquiryList) {

            InquiryDto inquiryDto = InquiryDto.from(inquiry, "문의사항");

            inquiryDtos.add(inquiryDto);
        }

        return inquiryDtos;
    }
}
