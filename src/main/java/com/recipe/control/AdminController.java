package com.recipe.control;

import com.recipe.dto.admin.NoticeDto;
import com.recipe.dto.admin.NoticeListDto;
import com.recipe.entity.admin.Notice;
import com.recipe.service.admin.InquiryService;
import com.recipe.service.admin.NoticeService;
import com.recipe.service.admin.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class AdminController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private InquiryService inquiryService;

    @GetMapping("/admin")
    public String adminPage() {

        return "main";
    }

    @GetMapping("/admin/report")
    public String reportPage(Model model) {

        model.addAttribute("reportList", reportService.getReports());
        return "admin/report";
    }

    @GetMapping("/admin/notice")
    public String noticePage(Model model) {

        // 전체 공지 가져오기
        List<NoticeListDto> allNotices = noticeService.getAllNoticeDtos();

        // 고정 리스트: 고정된 게시글만
        List<NoticeListDto> pinnedNotices = allNotices.stream()
                .filter(n -> n.isPinned() && !n.isHidden())
                .collect(Collectors.toList());

        // 일반 리스트: 모든 공지 포함 (고정도 포함) — 숨김된 것도 포함해야 함!
        List<NoticeListDto> noticeList = allNotices;

        System.out.println(noticeList.get(6).getAdminNickName());

        model.addAttribute("pinnedNotices", pinnedNotices);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pinnedCount", pinnedNotices.size());

        return "admin/notice";
    }

    // 📌 작성 폼 페이지
    @GetMapping("/admin/noticeWrite")
    public String showNoticeWriteForm(Model model) {
        model.addAttribute("noticeDto", new NoticeDto());
        return "admin/noticeWrite";
    }

    // 📌 작성 폼 제출 처리
    @PostMapping("/admin/noticeWrite")
    public String submitNotice(@ModelAttribute("notice") NoticeDto dto) {
        noticeService.saveNotice(dto);
        return "redirect:/admin/notice"; // 작성 완료 후 목록 페이지로 이동
    }

    // 공지사항 고정리스트
    @PostMapping("/admin/notice/pin")
    @ResponseBody
    public ResponseEntity<String> pinNotice(@RequestBody Long id) {
        try {
            noticeService.setPinned(id, true);
            return ResponseEntity.ok("success");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin/notice/unpin")
    @ResponseBody
    public String unpinNotice(@RequestBody Long id) {
        noticeService.setPinned(id, false);
        return "success";
    }

    @PostMapping("/admin/notice/hide")
    @ResponseBody
    public ResponseEntity<String> hideNotice(@RequestBody Long id) {

        try {
            noticeService.setHidden(id, true); // 여기서 고정해제도 같이 처리됨
            return ResponseEntity.ok("숨김 처리 완료");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin/notice/unhide")
    @ResponseBody
    public ResponseEntity<String> unhideNotice(@RequestBody Long id) {
        try {
            noticeService.setHidden(id, false);
            return ResponseEntity.ok("숨김 취소 완료");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 공지사항 상세페이지
    @GetMapping("/admin/noticeDetail/{noticeId}")
    public String noticeDetail(@PathVariable("noticeId") Long noticeId, Model model) {

        model.addAttribute("notice", noticeService.getNotice(noticeId));


        return "admin/noticeDetail";
    }

    @PostMapping("/admin/notice/update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateNotice(@PathVariable("id") Long id, @RequestBody Map<String, String> payload) {
        try {
            String title = payload.get("title");
            String content = payload.get("content");

            NoticeDto existing = noticeService.getNotice(id);
            existing.setTitle(title); // 제목 수정
            existing.setContent(content); // 내용 수정

            noticeService.saveNotice(existing);
            return ResponseEntity.ok("수정 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("수정 실패: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/notice/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) {
        try {
            noticeService.deleteNotice(id); // 서비스에 삭제 로직 추가 필요
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("삭제 실패: " + e.getMessage());
        }
    }

    @GetMapping("/admin/inquiry")
    public String inquiryPage(Model model) {

        model.addAttribute("inquiryList", inquiryService.getInquirys());
        return "admin/inquiry";
    }
}
