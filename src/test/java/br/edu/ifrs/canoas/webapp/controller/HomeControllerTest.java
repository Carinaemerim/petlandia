package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.service.AnnounceListService;
import br.edu.ifrs.canoas.webapp.service.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(HomeController.class)
public class HomeControllerTest extends BaseTest {
    @MockBean
    AnnounceListService announceListService;

    @MockBean
    SuggestionService suggestionService;

    @Test
    public void testHomeGet() {
        fail("Not implemented");
    }
}
