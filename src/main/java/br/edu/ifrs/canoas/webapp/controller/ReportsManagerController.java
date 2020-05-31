package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.CommentService;
import br.edu.ifrs.canoas.webapp.service.ReportService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/reports")
@AllArgsConstructor
public class ReportsManagerController {

    private final ReportService reportService;

    @GetMapping("/announces")
    public String getAnnounces(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("reports", reportService.findAll(page, 30, ReportStatus.WAITING_REVIEW, false));
        return "/manager/reports/list-announces";
    }

    @GetMapping("/comments")
    public String getComments(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("reports", reportService.findAll(page, 30, ReportStatus.WAITING_REVIEW, true));
        return "/manager/reports/list-comments";
    }
}
