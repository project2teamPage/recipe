package com.recipe.repository;

import com.recipe.entity.CalendarComment;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarCommentRepo extends JpaRepository<CalendarComment, Long> {
}
