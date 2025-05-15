package com.recipe.service.user;

import com.recipe.dto.user.CalendarCommentDto;
import com.recipe.dto.user.CalendarCommentListDto;
import com.recipe.entity.user.Calendar;
import com.recipe.entity.user.CalendarComment;
import com.recipe.entity.user.CalendarLike;
import com.recipe.entity.user.User;
import com.recipe.repository.user.CalendarCommentRepo;
import com.recipe.repository.user.CalendarLikeRepo;
import com.recipe.repository.user.CalendarRepo;
import com.recipe.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CalendarService{

    @Autowired
    private CalendarRepo calendarRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CalendarCommentRepo calendarCommentRepo;

    public void CommentService(CalendarCommentRepo calendarCommentRepo
            , UserRepo userRepo, CalendarRepo calendarRepo) {
        this.calendarCommentRepo = calendarCommentRepo;
        this.userRepo = userRepo;
        this.calendarRepo = calendarRepo;
    }

    // 댓글 조회
    public List<CalendarCommentDto> getCommentsCalendar(Long userId) {
        return calendarCommentRepo.findByUserIdOrderByWriteDateDesc(userId).stream()
                .map(comment -> {
                    CalendarCommentDto dto = new CalendarCommentDto();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setUserId(comment.getUser().getId());
                    dto.setNickName(comment.getUser().getNickName());
                    return dto;
                }).collect(Collectors.toList());
    }

    // 댓글 등록 dto->entity
    public void addComment(Long userId, CalendarCommentDto calendarCommentDto, String loginId) {
        User user = userRepo.findByLoginId(loginId);
        Calendar calendar = calendarRepo.findByUserId(userId).orElseThrow(); //. 댓글 등록하는 캘린더 엔티티 객체 조회.

//. 댓글은. 누가 어디에 작성하는 가 가 필요 - 그래서 작성자 엔티티와, 작성되는 캘린더 엔티티가 필용
        CalendarComment calendarComment = new CalendarComment();
        calendarComment.setContent(calendarCommentDto.getContent());
        calendarComment.setUser(user);
        calendarComment.setCalendar(calendar);

        calendarCommentRepo.save(calendarComment);
    }

    // 댓글 수정
    public void updateComment(Long calendarCommentId, String content) {

        CalendarComment calendarComment = calendarCommentRepo.findById(calendarCommentId).orElseThrow();
        calendarComment.setContent(content);
        calendarCommentRepo.save(calendarComment);
    }

    // 댓글 삭제
    public void deleteComment(Long calendarcommentId) {
        calendarCommentRepo.deleteById(calendarcommentId);
    }

    // 댓글 5개씩 가지고 오기 위한 메서드
    public Page<CalendarCommentListDto> getCommentList(Pageable pageable){

        List<CalendarCommentListDto> calendarCommentDtoList = new ArrayList<>();
        // 레퍼지토리를 통해 페이지 조회
        List<Comment> comments = calendarCommentRepo.findAllByOrderByIdDesc(pageable);

        // 전체 댓글 갯수
        Long total = calendarCommentRepo.count();

        // entity -> dto
        for(Comment comment : comments){
            CalendarCommentListDto calendarCommentListDto = CalendarCommentListDto.from(comment);
            calendarCommentDtoList.add(calendarCommentListDto);
        }

        return new PageImpl<>(calendarCommentDtoList, pageable, total);
    }


    // 좋아요 기능
//    private final CalendarLikeRepo calendarLikeRepo;
//
//    public CalendarService(CalendarLikeRepo calendarLikeRepo) {
//        this.calendarLikeRepo = calendarLikeRepo;
//    }
//
//    public void toggleLike(Long userId, Long calendarId) {
//        if (calendarLikeRepo.existsByCalendar_IdAndUser_Id(calendarId, userId)) {
//            calendarLikeRepo.deleteByCalendar_IdAndUser_Id(calendarId, userId);
//        } else {
//            CalendarLike like = new CalendarLike();
//            like.setUser();
//            like.setCalendar(calendarId);
//            calendarLikeRepo.save(like);
//        }
//    }

//    public int getLikeCount(Long calendarId) {
//        return calendarLikeRepo.countByCalendar_Id(calendarId);
//    }
}
