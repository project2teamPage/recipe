package com.recipe.repository.admin;

import com.recipe.entity.admin.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByWriteDateDesc(Pageable pageable);

    // 고정된 공지사항만 조회
    List<Notice> findAllByPinned(boolean pinned);

    // 고정된 공지사항 개수
    long countByPinned(boolean pinned);

    // 숨김상태가 아닌 공지사항만 조회
    List<Notice> findAllByHiddenFalse();

    List<Notice> findAllByPinnedTrueAndHiddenFalse();
}
