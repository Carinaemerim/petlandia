package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.repository.RoleRepository;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/manager/admin/users")
@AllArgsConstructor
public class UserAdminManagerController {

    private final UserService userService;
    private final RoleRepository roleRepository;

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
        model.addAttribute("userRole", Auth.getUserRole(user));
        model.addAttribute("roles", roleRepository.findAll());

        return "/manager/admin/user/view";
    }

    @PostMapping("/{id}/change-role")
    public String changeRole(@PathVariable("id") final Long id, @Valid @ModelAttribute("role") Role role) {
        User user = getUser(id);


        return "redirects:/manager/admin/users/" + user.getId();
    }

    @PostMapping("/{id}/block-user")
    public String blockUser(@PathVariable("id") final Long id) {
        User user = getUser(id);
        br.edu.ifrs.canoas.webapp.domain.Role role = roleRepository.findByRoleEquals(Role.ROLE_USER.name());
        user.setActive(false);
        user.getRoles().clear();
        user.getRoles().add(role);
        userService.save(user);
        return "redirects:/manager/admin/users/" + user.getId();
    }

    private User getUser(Long id) {
        User user = userService.findById(id);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }




}
