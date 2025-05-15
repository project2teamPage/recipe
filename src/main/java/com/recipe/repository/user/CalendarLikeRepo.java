package com.recipe.repository.user;

import com.recipe.entity.user.CalendarLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarLikeRepo extends JpaRepository<CalendarLike, Long> {
    // 특정 캘린더에 달린 좋아요 개수
    int countByCalendar_Id(Long calendarId);

    // 특정 캘린더에 대해 사용자가 좋아요 눌렀는지 확인
    boolean existsByCalendar_IdAndUser_Id(Long calendarId, Long userId);

    // 좋아요 취소할 때 사용
    void deleteByCalendar_IdAndUser_Id(Long calendarId, Long userId);
}
