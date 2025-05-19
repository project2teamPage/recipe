package com.recipe.repository.admin;

import com.recipe.constant.TargetType;
import com.recipe.entity.admin.Report;
import com.recipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByOrderByDateDesc();

    boolean existsByUserAndBannedUntilAfter(User user, LocalDateTime now);

    Optional<Report> findTopByTargetIdOrderByDateDesc(Long targetId);

    List<Report> findByTargetTypeAndTargetIdInOrderByDateDesc(TargetType targetType, List<Long> targetIds);

    @Query("SELECT r FROM Report r WHERE r.bannedUntil > :now")
    List<Report> findAllBannedUsers(@Param("now") LocalDateTime now);
}
