package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.forms.FilterForm;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.service.AnnounceListService;
import br.edu.ifrs.canoas.webapp.service.SuggestionService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest extends BaseTest {
    @MockBean
    AnnounceListService announceListService;

    @MockBean
    SuggestionService suggestionService;

    @Test
    public void testHomeGetLoggedIn() throws Exception {

        this.mockAuthContext.mockAuthModerator();

        List<FilterForm> filters = Collections.emptyList();
        List<Announce> suggestions = Lists.list(AnnounceHelper.createAnnounce());

        List<Announce> announces = Lists.emptyList();

        when(announceListService.getFilters()).thenReturn(filters);
        when(announceService.findFirstFive(AnnounceStatus.ACTIVE))
                .thenReturn(announces);

        when(suggestionService.findFirstFive(this.mockAuthContext.getUser()))
                .thenReturn(suggestions);

        this.mvc.perform(get("/")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filters", is(filters)))
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("/index"))
                .andExpect(model().attribute("suggestions", is(suggestions)))
                .andExpect(model().attribute("announces", is(announces)));

        verify(announceListService).getFilters();
        verify(announceService).findFirstFive(AnnounceStatus.ACTIVE);
        verify(suggestionService).findFirstFive(this.mockAuthContext.getUser());



    }

    @Test
    public void testHomeGetAnonymous() throws Exception {
        this.mockAuthContext.mockAuthAnonymous();

        List<FilterForm> filters = Collections.emptyList();

        List<Announce> announces = Lists.emptyList();

        when(announceListService.getFilters()).thenReturn(filters);
        when(announceService.findFirstFive(AnnounceStatus.ACTIVE))
                .thenReturn(announces);

        this.mvc.perform(get("/")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(view().name("/index"))
                .andExpect(model().attribute("filters", is(filters)))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attribute("suggestions", is(emptyCollectionOf(Announce.class))))
                .andExpect(model().attribute("announces", is(announces)));

        verify(announceListService).getFilters();
        verify(announceService).findFirstFive(AnnounceStatus.ACTIVE);
        verifyNoInteractions(suggestionService);

    }
}
