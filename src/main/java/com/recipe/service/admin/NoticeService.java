package com.recipe.service.admin;
import com.recipe.constant.Role;
import com.recipe.dto.admin.NoticeDto;
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

    public List<Notice> getPinnedNotices() {
        return noticeRepository.findAllByPinned(true);
    }

    // 고정 여부로 조회
    public List<NoticeListDto> getNotices(boolean pinned) {

        User user = userRepository.findByRole(Role.ADMIN);
        List<NoticeListDto> noticeListDtos = new ArrayList<>();
        List<Notice> noticeList = noticeRepository.findAllByPinned(pinned);

        for (Notice notice : noticeList) {
            noticeListDtos.add(NoticeListDto.from(notice, user.getNickName()));
        }

        return noticeListDtos;
    }

    // 전체 목록(고정 포함)
    public List<NoticeListDto> getNotices() {

        User user = userRepository.findByRole(Role.ADMIN);

        List<NoticeListDto> noticeListDtos = new ArrayList<>();

        List<Notice> noticeList = noticeRepository.findAll();

        for (Notice notice : noticeList) {

            NoticeListDto noticeListDto = NoticeListDto.from(notice, user.getNickName());

            noticeListDtos.add(noticeListDto);
        }

        return noticeListDtos;
    }

    // 상세페이지
    public NoticeDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
        User admin = userRepository.findByRole(Role.ADMIN);
        return NoticeDto.from(notice, admin.getNickName());
    }

}

