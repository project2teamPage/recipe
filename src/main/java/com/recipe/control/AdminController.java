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

        model.addAttribute("pinnedNotices", pinnedNotices);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pinnedCount", pinnedNotices.size());

        return "admin/notice";
    }

    // 📌 작성 폼 페이지
    @GetMapping("/admin/noticeWrite")
    public String showNoticeWriteForm(Model model) {
        model.addAttribute("notice", new NoticeDto());
        return "admin/noticeWrite";
    }

    // 📌 작성 폼 제출 처리
    @PostMapping("/admin/noticeWrite")
    public String submitNotice(@ModelAttribute("notice") NoticeDto dto) {
        noticeService.saveNotice(dto);
        return "redirect:/admin/notice"; // 작성 완료 후 목록 페이지로 이동
    }

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

    @GetMapping("/admin/noticeWrite")
    public String writeNoticePage(Model model) {

        model.addAttribute("noticeDto", new NoticeDto());
        return "admin/noticeWrite";
    }

    @GetMapping("/admin/noticeDetail/{noticeId}")
    public String noticeDetail(@PathVariable("noticeId") Long noticeId, Model model) {

        model.addAttribute("notice", noticeService.getNotice(noticeId));


        return "admin/noticeDetail";
    }

    @GetMapping("/admin/inquiry")
    public String inquiryPage(Model model) {

        model.addAttribute("inquiryList", inquiryService.getInquirys());
        return "admin/inquiry";
    }
}
