package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/admin/users")
@AllArgsConstructor
public class UserAdminManagerController {

    private final UserService userService;

    @GetMapping("")
    public String getActive(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "term", defaultValue = "") String term,
                            Model model) {

        model.addAttribute("term", term);
        model.addAttribute("users", userService.findAll(page, 30, term));
        return "/manager/admin/user/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable("id") final Long id, Model model) {
        User user = getUser(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "/manager/admin/user/view";
    }

    @PostMapping("/{id}/change-role")
    public String changeRole(@PathVariable("id") final Long id, @Valid @ModelAttribute("role") Role role) {
        User user = getUser(id);

        if (role == null) {
            throw new RuntimeException("role is not valid");
        }

        user.setRole(role);
        userService.save(user);

        return "redirect:/manager/admin/users/" + user.getId();
    }

    @PostMapping("/{id}/block-user")
    public String blockUser(@PathVariable("id") final Long id) {
        User user = getUser(id);
        user.setActive(false);
        user.setRole(Role.ROLE_USER);
        userService.save(user);
        return "redirect:/manager/admin/users/" + user.getId();
    }

    private User getUser(Long id) {
        User user = userService.findById(id);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }




}
