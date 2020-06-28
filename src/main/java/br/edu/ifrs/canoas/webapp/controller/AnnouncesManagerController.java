package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/announces")
@AllArgsConstructor
public class AnnouncesManagerController {

    private final UserService userService;
    private final AnnounceService announceService;

    @GetMapping("/active")
    public String getActive(@AuthenticationPrincipal UserImpl activeUser, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        return getUserAnnouncesByStatus(activeUser, page, AnnounceStatus.ACTIVE, model);
    }

    @GetMapping("/waiting-review")
    public String getWaitingReview(@AuthenticationPrincipal UserImpl activeUser, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        return getUserAnnouncesByStatus(activeUser, page, AnnounceStatus.WAITING_REVIEW, model);
    }

    @GetMapping("/blocked")
    public String getBlocked(@AuthenticationPrincipal UserImpl activeUser, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        return getUserAnnouncesByStatus(activeUser, page, AnnounceStatus.INACTIVE, model);
    }

    private String getUserAnnouncesByStatus(UserImpl activeUser, int page, AnnounceStatus status, Model model) {
        User user = userService.findById(activeUser.getUser().getId());
        if (user == null){
            throw new UserNotFoundException();
        }

        model.addAttribute("status", status);
        model.addAttribute("announces", announceService.findAll(page, user, status));
        return "/manager/announces/list";
    }
}
