package com.recipe.repository;

import com.recipe.entity.CalendarEntry;
import com.recipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEntryRepo extends JpaRepository<CalendarEntry, Long> {
}
