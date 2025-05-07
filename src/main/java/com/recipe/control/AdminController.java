package com.recipe.control;

import com.recipe.service.admin.InquiryService;
import com.recipe.service.admin.NoticeService;
import com.recipe.service.admin.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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
        return "report";
    }

    @GetMapping("/admin/notice")
    public String noticePage(Model model) {

        model.addAttribute("noticeList", noticeService.getNotices());

        return "notice";
    }

    @GetMapping("/admin/inquiry")
    public String inquiryPage(Model model) {

        model.addAttribute("inquiryList", inquiryService.getInquirys());
        return "inquiry";
    }
}
