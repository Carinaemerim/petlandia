package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.enums.UserStatus;
import br.edu.ifrs.canoas.webapp.exception.ForbiddenException;
import br.edu.ifrs.canoas.webapp.forms.UserSummary;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.service.ReportService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/admin/users")
@AllArgsConstructor
public class UserAdminManagerController {

    private final UserService userService;
    private final ReportService reportService;

    @GetMapping("")
    public String listUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "term", defaultValue = "") String term,
                            Model model) {
        model.addAttribute("term", term);
        model.addAttribute("users", userService.findAll(page, 30, term));
        return "/manager/admin/user/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable("id") final Long id,
                       Model model,
                       @AuthenticationPrincipal UserImpl activeUser) {
        boolean moderator = Auth.hasRole(new Role[]{Role.ROLE_MODERATOR});
        boolean admin = Auth.hasRole(new Role[]{Role.ROLE_ADMIN});
        User user = this.userService.findById(id);
        UserSummary summary = this.userService.getSummary(user);
        boolean isSelf = activeUser.getUser().getId().equals(id);
        boolean cantReport = isSelf ||
                !user.getStatus().equals(UserStatus.ACTIVE) ||
                !user.getRole().equals(Role.ROLE_USER);

        model.addAttribute("isSelf", isSelf);
        model.addAttribute("cantReport", cantReport);
        model.addAttribute("admin", admin);
        model.addAttribute("moderator", moderator);
        model.addAttribute("user", user);
        model.addAttribute("summary", summary);
        model.addAttribute("roles", Role.values());

        return "/manager/admin/user/view";
    }

    @PostMapping("/{id}/report")
    public String PostToReport(@AuthenticationPrincipal UserImpl activeUser,
                               @PathVariable("id") final Long id,
                               @ModelAttribute("message") String message) {
        boolean moderator = Auth.hasRole(new Role[]{Role.ROLE_MODERATOR});
        if (!moderator) {
            throw new ForbiddenException("not allowed");
        }

        User user = userService.findByIdByStatus(id, UserStatus.ACTIVE);
        if (!user.getRole().equals(Role.ROLE_USER)) {
            throw new ForbiddenException("not allowed");
        }

        if (activeUser.getUser().equals(user)) {
            throw new ForbiddenException("not allowed");
        }

        reportService.save(user, activeUser.getUser(), message);
        user.setStatus(UserStatus.WAITING_REVIEW);
        userService.save(user);
        return "redirect:/manager/admin/users/" + user.getId();
    }

    @PostMapping("/{id}/change-role")
    public String changeRole(@PathVariable("id") final Long id,
                             @Valid @ModelAttribute("role") Role role,
                             @AuthenticationPrincipal UserImpl activeUser) {
        boolean admin = Auth.hasRole(new Role[]{Role.ROLE_ADMIN});
        if (!admin) {
            throw new ForbiddenException("not allowed");
        }

        if (activeUser.getUser().getId().equals(id)) {
            throw new ForbiddenException("User cannot change his own privileges");
        }

        User user = this.userService.findById(id);

        if (role == null) {
            throw new RuntimeException("role is not valid");
        }

        user.setRole(role);
        userService.save(user);

        return "redirect:/manager/admin/users/" + user.getId();
    }

    @PostMapping("/{id}/block-user")
    public String blockUser(@PathVariable("id") final Long id,
                            @AuthenticationPrincipal UserImpl activeUser) {
        boolean admin = activeUser.getUser().getRole().equals(Role.ROLE_ADMIN);
        if (!admin) {
            throw new ForbiddenException("not allowed");
        }

        if (activeUser.getUser().getId().equals(id)) {
            throw new ForbiddenException("User cannot change his own privileges");
        }

        User user = this.userService.findById(id);
        this.userService.delete(user);

        return "redirect:/manager/admin/users/" + user.getId();
    }

}
