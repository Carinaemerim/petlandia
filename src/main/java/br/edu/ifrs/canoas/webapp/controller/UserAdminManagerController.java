package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/admin/users")
@AllArgsConstructor
public class UserAdminManagerController {

    private final UserService userService;
    private final AnnounceService announceService;

    @GetMapping("")
    public String getActive(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        return "/manager/admin/user/list";
    }




}
