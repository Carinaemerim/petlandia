package br.edu.ifrs.canoas.webapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {

    @GetMapping("")
    public String index(Model model) {


        return "/manager/index";
    }

}
