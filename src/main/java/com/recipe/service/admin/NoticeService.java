package com.recipe.service.admin;
import com.recipe.constant.Role;
import com.recipe.dto.admin.NoticeListDto;
import com.recipe.entity.admin.Notice;
import com.recipe.entity.user.User;
import com.recipe.repository.admin.NoticeRepository;
import com.recipe.repository.user.UserRepository;
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

            NoticeListDto noticeListDto = NoticeListDto.from(notice, user.getNickName());

            noticeListDtos.add(noticeListDto);
        }

        return noticeListDtos;
    }
}
