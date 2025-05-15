package com.recipe.control;

import com.recipe.constant.Role;
import com.recipe.dto.admin.NoticeDto;
import com.recipe.dto.admin.NoticeListDto;
import com.recipe.dto.admin.ReportListDto;
import com.recipe.entity.admin.Report;
import com.recipe.service.admin.InquiryService;
import com.recipe.service.admin.NoticeService;
import com.recipe.service.admin.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private InquiryService inquiryService;

    // 관리자 페이지
    @GetMapping("/admin")
    public String adminPage() {
        return "main";
    }

    // 신고 관리 페이지
    @GetMapping("/admin/report")
    public String reportPage(Model model) {
        model.addAttribute("reportList", reportService.getReports());
        return "admin/report";
    }

    // 공지사항 관리 페이지 (사용자 역할에 따라 공지사항 다르게 처리)
    @GetMapping("/admin/notice")
    public String noticePage(@RequestParam(value = "role", required = false) Role role, Model model) {
        // 로그인 연동이 안 되어 있을 때 임시로 관리자 역할을 하드코딩
        if (role == null) {
            role = Role.ADMIN;  // 임시로 관리자로 설정
        }

        List<NoticeListDto> noticeList = noticeService.getNoticesByRole(role);

        // 고정된 공지사항은 별도로 처리
        List<NoticeListDto> pinnedNotices = noticeService.getPinnedNoticeDtos();

        model.addAttribute("pinnedNotices", pinnedNotices);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pinnedCount", pinnedNotices.size());
        return "admin/notice";
    }

    // 공지사항 작성 페이지
    @GetMapping("/admin/noticeWrite")
    public String showNoticeWriteForm(Model model) {
        model.addAttribute("noticeDto", new NoticeDto());
        return "admin/noticeWrite";
    }

    // 공지사항 작성 처리
    @PostMapping("/admin/noticeWrite")
    public String createNotice(@ModelAttribute("noticeDto") NoticeDto dto) {
        noticeService.saveNotice(dto);
        return "redirect:/admin/notice";
    }

    // 공지사항 고정 처리
    @PostMapping("/admin/notice/pin")
    @ResponseBody
    public ResponseEntity<String> pinNotice(@RequestBody Long id) {
        return handleServiceCall(() -> noticeService.setPinned(id, true), "고정 완료");
    }

    // 공지사항 고정 해제 처리
    @PostMapping("/admin/notice/unpin")
    @ResponseBody
    public ResponseEntity<String> unpinNotice(@RequestBody Long id) {
        return handleServiceCall(() -> noticeService.setPinned(id, false), "고정 해제 완료");
    }

    // 공지사항 숨김 처리
    @PostMapping("/admin/notice/hide")
    @ResponseBody
    public ResponseEntity<String> hideNotice(@RequestBody Long id) {
        return handleServiceCall(() -> noticeService.setHidden(id, true), "숨김 처리 완료");
    }

    // 공지사항 숨김 취소 처리
    @PostMapping("/admin/notice/unhide")
    @ResponseBody
    public ResponseEntity<String> unhideNotice(@RequestBody Long id) {
        return handleServiceCall(() -> noticeService.setHidden(id, false), "숨김 취소 완료");
    }

    // 공지사항 상세보기
    @GetMapping("/admin/noticeDetail/{noticeId}")
    public String noticeDetail(@PathVariable("noticeId") Long noticeId, Model model) {
        model.addAttribute("notice", noticeService.getNotice(noticeId));
        return "admin/noticeDetail";
    }

    // 공지사항 수정 처리
    @PostMapping("/admin/notice/update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateNotice(@PathVariable("id") Long id, @RequestBody Map<String, String> payload) {
        return handleServiceCall(() -> {
            String title = payload.get("title");
            String content = payload.get("content");
            NoticeDto existing = noticeService.getNotice(id);
            existing.setTitle(title);
            existing.setContent(content);
            noticeService.saveNotice(existing);
        }, "수정 완료");
    }

    // 공지사항 삭제 처리
    @DeleteMapping("/admin/notice/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) {
        return handleServiceCall(() -> noticeService.deleteNotice(id), "삭제 완료");
    }

    // 문의 관리 페이지
    @GetMapping("/admin/inquiry")
    public String inquiryPage(Model model) {
        model.addAttribute("inquiryList", inquiryService.getInquirys());
        return "admin/inquiry";
    }

    // 서비스 호출 시 성공/실패 응답 처리
    private ResponseEntity<String> handleServiceCall(Runnable action, String successMsg) {
        try {
            action.run();
            return ResponseEntity.ok(successMsg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("실패: " + e.getMessage());
        }
    }
}
