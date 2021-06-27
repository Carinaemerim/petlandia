package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportType;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.helper.URIHelper;
import br.edu.ifrs.canoas.webapp.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager/reports")
@AllArgsConstructor
public class ReportsManagerController {

    private static final int PAGE_LENGTH = 10;

    private final ReportService reportService;
    private final Messages messages;

    @GetMapping("/announces")
    public String getAnnounces(@RequestParam(value = "page", defaultValue = "0") int page, Model model,
                               HttpServletRequest httpServletRequest) {
        model.addAttribute("reports", reportService.findAllByStatusAndType(page, PAGE_LENGTH, ReportStatus.WAITING_REVIEW, ReportType.ANNOUNCE));
        model.addAttribute("referrer", URIHelper.getReferrerURI(httpServletRequest, "/announces"));
        return "/manager/reports/list-announces";
    }

    @GetMapping("/comments")
    public String getComments(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("reports", reportService.findAllByStatusAndType(page, PAGE_LENGTH, ReportStatus.WAITING_REVIEW, ReportType.COMMENT));
        return "/manager/reports/list-comments";
    }

    @GetMapping("/users")
    public String getUsers(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        boolean admin = Auth.hasRole(new Role[]{Role.ROLE_ADMIN});
        model.addAttribute("reports", reportService.findAllByStatusAndType(page, PAGE_LENGTH, ReportStatus.WAITING_REVIEW, ReportType.USER));
        model.addAttribute("admin", admin);
        return "/manager/reports/list-users";
    }

    @PostMapping("/{id}/accept")
    public String postReportAccept(@PathVariable("id") final Long id,
                                   @AuthenticationPrincipal UserImpl activeUser,
                                   RedirectAttributes redirectAttributes) {
        String page = this.reportService.action(id, activeUser.getUser(), ReportStatus.ACCEPTED);
        redirectAttributes.addFlashAttribute("success", "report." + page + ".accepted");
        return "redirect:/manager/reports/" + page;
    }

    @PostMapping("/{id}/reject")
    public String postReportReject(@PathVariable("id") final Long id,
                                   @AuthenticationPrincipal UserImpl activeUser,
                                   RedirectAttributes redirectAttributes) {
        String page = this.reportService.action(id, activeUser.getUser(), ReportStatus.REJECTED);
        redirectAttributes.addFlashAttribute("success", "report." + page + ".rejected");
        return "redirect:/manager/reports/" + page;
    }
}
