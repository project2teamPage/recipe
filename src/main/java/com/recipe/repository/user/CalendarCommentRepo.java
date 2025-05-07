package com.recipe.repository.user;

import com.recipe.entity.user.CalendarComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarCommentRepo extends JpaRepository<CalendarComment, Long> {
}
