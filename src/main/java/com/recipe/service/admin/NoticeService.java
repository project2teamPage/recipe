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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public void saveNotice(NoticeDto dto) {

//        User admin = userRepository.findByRole(Role.ADMIN);


        Notice notice;

        if (dto.getId() != null) {
            // 기존 공지 불러오기
            notice = noticeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
            notice.setTitle(dto.getTitle());
            notice.setContent(dto.getContent());
            notice.setUpdateDate(LocalDateTime.now());

        } else {
            // 새 공지 생성
            notice = new Notice();
            notice.setTitle(dto.getTitle());
            notice.setContent(dto.getContent());
            notice.setWriteDate(LocalDateTime.now());
            notice.setPinned(false);
            notice.setHidden(false);

            // admin 설정
            User admin = userRepository.findByRole(Role.ADMIN);
            notice.setAdmin(admin);
        }

        noticeRepository.save(notice);
    }

    // ✅ 유지: pin/unpin 기능에 필요
    public void setPinned(Long noticeId, boolean pinned) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

        // 고정하려는 경우만 개수 체크
        if (pinned) {
            long pinnedCount = noticeRepository.countByPinned(true);
            if (pinnedCount >= 4) {
                throw new IllegalStateException("고정 가능한 최대 개수(4개)를 초과했습니다.");
            }

            // 2️⃣ 고정 시 숨김 해제
            if (notice.isHidden()) {
                notice.setHidden(false);
            }
        }

        notice.setPinned(pinned);
        noticeRepository.save(notice);
    }

    public void setHidden(Long noticeId, boolean hidden) {
        Notice notice = noticeRepository.findById(noticeId)
            .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

        notice.setHidden(hidden);

        if (hidden) {
            notice.setPinned(false); // 숨기면 고정도 해제
        }
        noticeRepository.save(notice);
    }
    public List<NoticeListDto> getAllNoticeDtos() {

//        User admin = userRepository.findByRole(Role.ADMIN);
        List<Notice> allNotices = noticeRepository.findAll();
        List<NoticeListDto> noticeListDtos = new ArrayList<>();

        for (Notice notice : allNotices) {
//            noticeListDtos.add(NoticeListDto.from(notice, admin.getNickName()));
            noticeListDtos.add(NoticeListDto.from(notice, "관리자")); // 임시코딩
        }

        return noticeListDtos;
    }

    // 상세페이지
    public NoticeDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
//        User admin = userRepository.findByRole(Role.ADMIN);
//        return NoticeDto.from(notice, admin.getNickName());
        return NoticeDto.from(notice, "관리자"); // 임시코딩
    }

    // ✅ 추가: 고정된 공지사항 DTO 목록 반환
    public List<NoticeListDto> getPinnedNoticeDtos() {
        User admin = userRepository.findByRole(Role.ADMIN);
        List<Notice> pinnedNotices = noticeRepository.findAllByPinnedTrueAndHiddenFalse();

        List<NoticeListDto> dtoList = new ArrayList<>();

        for (Notice notice : pinnedNotices) {
//            dtoList.add(NoticeListDto.from(notice, admin.getNickName()));
            dtoList.add(NoticeListDto.from(notice, "관리자")); // 임시코딩
        }

        return dtoList;
    }


    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}

