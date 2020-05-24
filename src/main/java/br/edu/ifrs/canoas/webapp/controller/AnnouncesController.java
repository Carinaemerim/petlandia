package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/announces")
@AllArgsConstructor
public class AnnouncesController {

    private static final int PAGE_LENGTH = 10;
    private static final int PAGE_COMMENT_LENGTH = 10;

    private final Messages messages;
    private final AnnounceService announceService;
    private final CommentService commentService;
    private final AnnounceListService announceListService;

    @GetMapping
    public String getList(@ModelAttribute AnnounceFilterForm form, Model model) {
        if (form == null) {
            form = new AnnounceFilterForm();
        }

        PaginatedEntity<Announce> announces = announceService.findAll(form, AnnounceStatus.ACTIVE, PAGE_LENGTH);
        model.addAttribute("filters", announceListService.getFilters());
        model.addAttribute("form", form);
        model.addAttribute("announces", announces);

        return "/announce/list";
    }

    @GetMapping("/{id}")
    public String announceDetails(@PathVariable("id") final String id,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  Model model) {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null) {
            throw new AnnounceNotFoundException();
        }

        model.addAttribute("comments", commentService.findAll(page, PAGE_COMMENT_LENGTH, announce));
        model.addAttribute("announce", announce);
        return "/announce/announceDetails";
    }
}
