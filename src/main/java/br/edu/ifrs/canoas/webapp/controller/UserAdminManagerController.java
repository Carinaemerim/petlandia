package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String editGet(@PathVariable("id") final Long id, Model model) {
        User user = userService.findById(id);

        if (user == null) {
            throw new UserNotFoundException();
        }

        model.addAttribute("user", user);

        return "/manager/admin/user/view";
    }
}
