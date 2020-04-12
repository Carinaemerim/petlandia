package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

        PaginatedEntity<Announce> announces = announceService.findAllByFilter(form);
        model.addAttribute("filters", announceListService.getFilters());
        model.addAttribute("form", form);
        model.addAttribute("announces", announces);

        return "/announce/list";
    }

    @GetMapping("/{id}")
    public String announceDetails(@PathVariable("id") final String id, Model model) {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null) {
            return "/notFound";
        }

        model.addAttribute("announce", announce);
        return "/announce/announceDetails";
    }
}
