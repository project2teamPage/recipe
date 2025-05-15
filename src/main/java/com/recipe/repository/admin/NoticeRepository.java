package com.recipe.repository.admin;

import com.recipe.entity.admin.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByOrderByWriteDateDesc(Pageable pageable);

    // ✅ 고정된 공지사항만 조회 (기존)
    List<Notice> findAllByPinned(boolean pinned);

    // ✅ 고정된 공지사항 개수 (정확)
    long countByPinnedTrue();

    // ✅ 숨김상태가 아닌 공지사항만 조회 (기존)
    List<Notice> findAllByHiddenFalse();

    // ✅ 기존: 고정글 + 숨김 제외
    List<Notice> findAllByPinnedTrueAndHiddenFalse();

    // 고정글 + 숨김 제외 + 최신순 (수정일 우선)
    @Query("SELECT n FROM Notice n WHERE n.pinned = true AND n.hidden = false ORDER BY COALESCE(n.updateDate, n.writeDate) DESC")
    List<Notice> findAllPinnedVisibleOrderByLatestDateDesc();

    // 일반글 (고정 X) + 숨김 제외 + 최신순 (수정일 우선)
    @Query("SELECT n FROM Notice n WHERE n.hidden = false ORDER BY COALESCE(n.updateDate, n.writeDate) DESC")
    List<Notice> findAllVisibleOrderByLatestDateDesc();

    // 숨김 처리되지 않은 모든 공지사항을 최신순으로 조회
    @Query("SELECT n FROM Notice n WHERE n.hidden = false ORDER BY COALESCE(n.updateDate, n.writeDate) DESC")
    List<Notice> findAllOrderByLatestDateDesc();

}
