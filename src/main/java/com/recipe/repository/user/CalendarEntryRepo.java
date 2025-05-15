package com.recipe.repository.user;

import com.recipe.entity.user.CalendarEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEntryRepo extends JpaRepository<CalendarEntry, Long> {
}
