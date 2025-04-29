package com.recipe.repository;

import com.recipe.entity.CalendarLike;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarLikeRepo extends JpaRepository<CalendarLike, Long> {
}
