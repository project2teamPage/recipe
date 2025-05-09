package com.recipe.repository.admin;

import com.recipe.entity.admin.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByWriteDateDesc(Pageable pageable);
    List<Notice> findAllByPinned(boolean pinned);

    long countByPinned(boolean pinned);
}
