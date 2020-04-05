package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller
@RequestMapping("/announces")
@AllArgsConstructor
public class AnnouncesController {

    private final Messages messages;
    private final AnnounceService announceService;
    private final AnnounceListService announceListService;

    @GetMapping
    public String getList(@ModelAttribute AnnounceFilterForm form, Model model) {
        if (form == null) {
            form = new AnnounceFilterForm();
        }

        model.addAttribute("filters", announceListService.getFilters());
        model.addAttribute("form", form);
        model.addAttribute("announces", announceService.listAnnounce());

        return "/announce/list";
    }



}
