package com.recipe.repository.user;

import com.recipe.entity.user.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;
import java.util.Optional;

@Repository
public interface CalendarRepo extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByUserId(Long userId);
    Page<Comment> findAllByOrderByIdDesc(Pageable pageable);
}
