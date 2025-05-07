package com.recipe.repository.user;

import com.recipe.entity.user.CalendarEntryMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEntryMealRepo extends JpaRepository<CalendarEntryMeal, Long> {
}
