package com.recipe.repository.user;

import com.recipe.entity.user.CalendarComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;
import java.util.Arrays;
import java.util.List;

@Repository
public interface CalendarCommentRepo extends JpaRepository<CalendarComment, Long> {


    List<CalendarComment> findByUserIdOrderByWriteDateDesc(Long userId);

    List<Comment> findAllByOrderByIdDesc(Pageable pageable);

}
