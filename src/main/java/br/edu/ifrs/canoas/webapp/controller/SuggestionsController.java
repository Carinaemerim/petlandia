package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/suggestions")
@AllArgsConstructor
public class SuggestionsController {
    private final SuggestionService suggestionService;

    @GetMapping("")
    public String index(@AuthenticationPrincipal UserImpl activeUser,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        model.addAttribute("suggestions", suggestionService.findAllByUser(activeUser.getUser(), AnnounceStatus.ACTIVE, pageable));
        return "/suggestions/index";
    }
}
