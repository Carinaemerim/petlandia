package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.service.AnnounceListService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(AnnouncesController.class)
public class AnnouncesControllerTest extends BaseTest {
    @MockBean
    AnnounceListService announceListService;

    @Test
    public void testGetListGet() {
        fail("Not implemented");
    }
}
