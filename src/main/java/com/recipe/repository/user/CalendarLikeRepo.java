package com.recipe.repository.user;

import com.recipe.entity.user.CalendarLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarLikeRepo extends JpaRepository<CalendarLike, Long> {
}
