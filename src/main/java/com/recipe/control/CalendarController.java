package com.recipe.control;

import com.recipe.config.CustomUserDetails;
import com.recipe.dto.user.CalendarCommentDto;
import com.recipe.dto.user.CalendarCommentListDto;
import com.recipe.entity.user.User;
import com.recipe.service.user.CalendarService;
import com.recipe.service.user.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private UserService userService;

    public void CommentController(CalendarService calendarService, UserService userService) {
        this.calendarService = calendarService;
        this.userService = userService;
    }

    // 내 캘린더
    @GetMapping("/user/calendar/{userId}")
    public String calendar(@PathVariable Long userId, Model model, Principal principal){
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);


        // 내 캘린더 다른 회원 캘린더 구분
        User targetUser = userService.getUserById(userId); // 캘린더 주인
        model.addAttribute("user", targetUser);

        // 로그인한 사용자와 비교
        boolean isOwner = false;
        if (principal != null) {
            String loginId = principal.getName();
            User currentUser = userService.getUserByLoginId(loginId);
            if (currentUser.getId().equals(userId)) {
                isOwner = true;
            }
        }

        List<CalendarCommentDto> calendarCommentListDtos = calendarService.getCommentsCalendar(userId);
        model.addAttribute("comments", calendarCommentListDtos );
        model.addAttribute("addcomment", new CalendarCommentDto());

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("addComment", new CalendarCommentDto() );

        return "user/calendar";
    }

//    // 댓글 출력
//    @GetMapping("/user/calendar/comment/{calendarId}")
//    public String viewComment(@PathVariable Long calendarId, Model model){
//        List<CalendarCommentDto> calendarCommentListDtos = calendarService.getCommentsCalendar(calendarId);
//        model.addAttribute("comments", calendarCommentListDtos );
//        model.addAttribute("addcomment", new CalendarCommentDto());
//
//        return "/user/calendar/"+calendarId;
//    }


    // 댓글 작성(받아옴)
    @PostMapping("/user/calendar/{userId}")
    public String addComment(@PathVariable Long userId,
                             @ModelAttribute("addComment") CalendarCommentDto calendarCommentDto,
                             Principal principal){

        calendarService.addComment( userId, calendarCommentDto , principal.getName());
        return "redirect:/user/calendar/"+userId;
    }

    // 댓글 수정
    @PutMapping("/user/{calendarCommentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long calendarCommentId, @RequestParam String content){
        calendarService.updateComment(calendarCommentId, content);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/user/{calendarCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long calendarcommentId){
        calendarService.deleteComment(calendarcommentId);
        return ResponseEntity.ok().build();
    }

    // 캘린더 댓글 목록 페이징 처리
    @GetMapping("/user/calendar/{userId}/{CommentPage}")
    public String commentHome(@PathVariable("CommentPage") Integer pageNum, Model model){
        int currentPage = (pageNum != null ) ? pageNum : 0;
        Pageable pageable = PageRequest.of(currentPage, 5);

        Page<CalendarCommentListDto> calendarCommentListDtos = calendarService.getCommentList(pageable);

        model.addAttribute("calendarCommentListDtos", calendarCommentListDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "user/calendar";
    }
}
