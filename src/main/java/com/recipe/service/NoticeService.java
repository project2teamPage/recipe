package com.recipe.service;

import com.recipe.constant.Role;
import com.recipe.dto.NoticeListDto;
import com.recipe.entity.Notice;
import com.recipe.entity.User;
import com.recipe.repository.NoticeRepository;
import com.recipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public List<NoticeListDto> getNotices() {

        User user = userRepository.findByRole(Role.ADMIN);

        List<NoticeListDto> noticeListDtos = new ArrayList<>();

        List<Notice> noticeList = noticeRepository.findAll();

        for(Notice notice : noticeList) {

            NoticeListDto noticeListDto = NoticeListDto.from(notice, "공지사항");

            noticeListDtos.add(noticeListDto);
        }

        return noticeListDtos;
    }
}
