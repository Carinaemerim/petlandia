package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.service.AnnounceListService;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final AnnounceListService announceListService;
    private final AnnounceService announceService;
    private final SuggestionService suggestionService;

    @GetMapping("")
    public String home(@AuthenticationPrincipal UserImpl activeUser, Model model) {
        List<Announce> suggestions = new ArrayList<>();

        if (activeUser != null) {
            suggestions = suggestionService.findFirstFive(activeUser.getUser());
        }

        model.addAttribute("filters", announceListService.getFilters());
        model.addAttribute("form", new AnnounceFilterForm());
        model.addAttribute("announces", announceService.findFirstFive(AnnounceStatus.ACTIVE));
        model.addAttribute("suggestions", suggestions);
        return "/index";
    }
}
