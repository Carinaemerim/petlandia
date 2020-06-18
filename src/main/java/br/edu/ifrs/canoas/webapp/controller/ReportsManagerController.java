package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.Report;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/reports")
@AllArgsConstructor
public class ReportsManagerController {

    private static final int PAGE_LENGTH = 10;

    private final ReportService reportService;

    @GetMapping("/announces")
    public String getAnnounces(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("reports", reportService.findAllAnnounces(page, PAGE_LENGTH, ReportStatus.WAITING_REVIEW));
        return "/manager/reports/list-announces";
    }

    @GetMapping("/comments")
    public String getComments(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("reports", reportService.findAllComments(page, PAGE_LENGTH, ReportStatus.WAITING_REVIEW));
        return "/manager/reports/list-comments";
    }

    @PostMapping("/{id}/accept")
    public String postReportAccept(@PathVariable("id") final Long id,
                                   @AuthenticationPrincipal UserImpl activeUser) {
        String page = this.reportService.action(id, activeUser.getUser(), ReportStatus.ACCEPTED);
        return "redirect:/manager/reports/" + page;
    }

    @PostMapping("/{id}/reject")
    public String postReportReject(@PathVariable("id") final Long id,
                                   @AuthenticationPrincipal UserImpl activeUser) {
        String page = this.reportService.action(id, activeUser.getUser(), ReportStatus.REJECTED);
        return "redirect:/manager/reports/" + page;
    }
}