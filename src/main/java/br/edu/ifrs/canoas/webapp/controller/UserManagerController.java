package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/user")
@AllArgsConstructor
public class UserManagerController {

    private final UserService userService;
    private final Messages messages;
    private final AnimalCastratedService animalCastratedService;
    private final AnimalGenderService animalGenderService;
    private final AnimalSizeService animalSizeService;
    private final AnimalTypeService animalTypeService;
    private final AnimalAgeService animalAgeService;
    private final AnimalColorService animalColorService;

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal UserImpl activeUser, Model model) {
        User user = userService.findById(activeUser.getUser().getId());
        if (user == null){
            return "/notFound";
        }

        model.addAttribute("user", user);
        return "/manager/user/profile";
    }

    @GetMapping("/profile/edit")
    public String getProfileEdit(@AuthenticationPrincipal UserImpl activeUser, Model model) {
        User user = userService.findById(activeUser.getUser().getId());
        if (user == null){
            return "/notFound";
        }

        model.addAttribute("user", user);
        return "/manager/user/edit";
    }
}
