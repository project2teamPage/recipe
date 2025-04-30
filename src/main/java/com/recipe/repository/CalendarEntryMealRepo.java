package com.recipe.repository;

import com.recipe.entity.CalendarEntryMeal;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEntryMealRepo extends JpaRepository<CalendarEntryMeal, Long> {
}
