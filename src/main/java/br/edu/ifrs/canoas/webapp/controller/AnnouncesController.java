package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.helper.URIHelper;
import br.edu.ifrs.canoas.webapp.service.AnnounceListService;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public String announceDetails(@PathVariable("id") final Long id,
                                  @AuthenticationPrincipal UserImpl activeUser,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  HttpServletRequest httpServletRequest,
                                  Model model) {

        Announce announce = announceService.findByIdAndCheck(id);
        User currentUser = Auth.getUser();
        Long currentUserId = currentUser != null ? currentUser.getId() : 0L;

        model.addAttribute("comments", commentService.findAll(page, PAGE_COMMENT_LENGTH, announce, CommentStatus.ACTIVE));
        model.addAttribute("announce", announce);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("isAdmin", activeUser != null && activeUser.getUser().getRole() == Role.ROLE_ADMIN);
        model.addAttribute("referrer", URIHelper.getReferrerURI(httpServletRequest, "/announces"));
        return "/announce/announceDetails";
    }
}
