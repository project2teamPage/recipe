package com.recipe.control;

import com.recipe.dto.admin.NoticeDto;
import com.recipe.entity.admin.Notice;
import com.recipe.service.admin.InquiryService;
import com.recipe.service.admin.NoticeService;
import com.recipe.service.admin.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


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
        return "/admin/report";
    }

    @GetMapping("/admin/notice")
    public String noticePage(Model model) {

        model.addAttribute("noticeList", noticeService.getNotices());

        return "/admin/notice";
    }

    @GetMapping("/admin/inquiry")
    public String inquiryPage(Model model) {

        model.addAttribute("inquiryList", inquiryService.getInquirys());
        return "/admin/inquiry";
    }

    @GetMapping("/admin/noticeWrite")
    public String writeNoticePage(Model model) {

        model.addAttribute("noticeDto", new NoticeDto());
        return "/admin/noticeWrite";
    }

    @GetMapping("/admin/noticeDetail/{noticeId}")
    public String noticeDetail(@PathVariable("noticeId") Long noticeId, Model model) {

        model.addAttribute("notice", noticeService.getNotice(noticeId));

        return "admin/noticeDetail";
    }
}
