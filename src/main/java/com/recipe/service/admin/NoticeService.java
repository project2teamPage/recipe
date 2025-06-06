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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public void saveNotice(NoticeDto dto) {
        Notice notice = (dto.getId() != null) ?
                noticeRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다.")) :
                new Notice();

        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        if (dto.getId() != null) {
            notice.setUpdateDate(LocalDateTime.now());
        } else {
            notice.setWriteDate(LocalDateTime.now());
            notice.setPinned(false);
            notice.setHidden(false);

            User admin = userRepository.findByRole(Role.ADMIN);
            if (admin != null) {
                notice.setAdmin(admin);
            } else {
                admin = new User();
                admin.setRole(Role.ADMIN);
                notice.setAdmin(admin);
            }
        }

        noticeRepository.save(notice);
    }

    public List<NoticeListDto> getNoticesByRole(Role role) {
        if (role == Role.ADMIN) {
            return noticeRepository.findAllOrderByLatestDateDesc().stream()
                    .map(notice -> NoticeListDto.from(notice, getAdminNickName(notice)))
                    .collect(Collectors.toList());
        } else {
            return noticeRepository.findAllVisibleOrderByLatestDateDesc().stream()
                    .map(notice -> NoticeListDto.from(notice, getAdminNickName(notice)))
                    .collect(Collectors.toList());
        }
    }

    public void setPinned(Long noticeId, boolean pinned) {
        Notice notice = getNoticeOrThrow(noticeId);

        if (pinned) {
            long pinnedCount = noticeRepository.countByPinnedTrue();
            if (pinnedCount >= 4) {
                throw new IllegalStateException("고정 가능한 최대 개수(4개)를 초과했습니다.");
            }
            if (notice.isHidden()) {
                notice.setHidden(false);
            }
        }

        notice.setPinned(pinned);
        noticeRepository.save(notice);
    }

    public void setHidden(Long noticeId, boolean hidden) {
        Notice notice = getNoticeOrThrow(noticeId);
        notice.setHidden(hidden);
        if (hidden) {
            notice.setPinned(false);
        }
        noticeRepository.save(notice);
    }

    public List<NoticeListDto> getAllNoticeDtos() {
        return noticeRepository.findAllVisibleOrderByLatestDateDesc().stream()
                .map(notice -> NoticeListDto.from(notice, getAdminNickName(notice)))
                .collect(Collectors.toList());
    }

    public List<NoticeListDto> getPinnedNoticeDtos() {
        return noticeRepository.findAllPinnedVisibleOrderByLatestDateDesc().stream()
                .map(notice -> NoticeListDto.from(notice, getAdminNickName(notice)))
                .collect(Collectors.toList());
    }

    public NoticeDto getNotice(Long noticeId) {
        Notice notice = getNoticeOrThrow(noticeId);
        return NoticeDto.from(notice, getAdminNickName(notice));
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    private Notice getNoticeOrThrow(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
    }

    private String getAdminNickName(Notice notice) {
        return (notice.getAdmin() != null && notice.getAdmin().getNickName() != null)
                ? notice.getAdmin().getNickName()
                : "관리자";
    }

    public NoticeDto getNoticeForUser(Long noticeId) {
        Notice notice = getNoticeOrThrow(noticeId);
        if (notice.isHidden()) {
            throw new RuntimeException("숨김 처리된 공지사항입니다.");
        }
        return NoticeDto.from(notice, getAdminNickName(notice));
    }
}
