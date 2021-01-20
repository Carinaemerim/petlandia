package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(ReportsManagerController.class)
public class ReportsManagerControllerTest extends BaseTest {
    @MockBean
    ReportService reportService;

    @Test
    public void testGetAnnouncesGet() {
        fail("Not implemented");
    }
}
