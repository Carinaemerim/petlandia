package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.helper.PaginatedEntityHelper;
import br.edu.ifrs.canoas.webapp.service.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SuggestionsController.class)
public class SuggestionsControllerTest extends BaseTest {
    @MockBean
    SuggestionService suggestionService;

    @Test
    public void testIndexGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
        );

        when(suggestionService.findAllByUser(
                this.mockAuthContext.getUser(),
                AnnounceStatus.ACTIVE,
                PageRequest.of(0, 10)
                )
        ).thenReturn(announces);

        this.mvc.perform(get("/suggestions")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("suggestions", is(announces)))
                .andExpect(view().name("/suggestions/index"));

        verify(suggestionService).findAllByUser(
                this.mockAuthContext.getUser(),
                AnnounceStatus.ACTIVE,
                PageRequest.of(0, 10)
        );
    }
}
