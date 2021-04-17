package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.Report;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.helper.PaginatedEntityHelper;
import br.edu.ifrs.canoas.webapp.helper.ReportHelper;
import br.edu.ifrs.canoas.webapp.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportsManagerController.class)
public class ReportsManagerControllerTest extends BaseTest {
    @MockBean
    ReportService reportService;

    @Test
    public void testGetAnnouncesGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Report> reports = PaginatedEntityHelper.createPaginatedEntity(
                Report.class,
                ReportHelper.class.getMethod("createReport"),
                null,
                1,
                10
        );

        when(this.reportService.findAllAnnounces(
                0,
                10,
                ReportStatus.WAITING_REVIEW
        )).thenReturn(reports);

        this.mvc.perform(get("/manager/reports/announces")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("reports", is(reports)))
                .andExpect(view().name("/manager/reports/list-announces"))
                .andExpect(model().attribute("referrer", is(matchesPattern("^.*/announces$"))));

        verify(reportService).findAllAnnounces(
                0,
                10,
                ReportStatus.WAITING_REVIEW
        );
    }

    @Test
    public void testGetCommentsGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Report> reports = PaginatedEntityHelper.createPaginatedEntity(
                Report.class,
                ReportHelper.class.getMethod("createReport"),
                null,
                1,
                10
        );

        when(this.reportService.findAllComments(
                0,
                10,
                ReportStatus.WAITING_REVIEW
        )).thenReturn(reports);

        this.mvc.perform(get("/manager/reports/comments")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("reports", is(reports)))
                .andExpect(view().name("/manager/reports/list-comments"));

        verify(reportService).findAllComments(
                0,
                10,
                ReportStatus.WAITING_REVIEW
        );
    }

    @Test
    public void testPostReportAccept() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        Long id = 1L;

        when(this.reportService.action(
                id,
                this.mockAuthContext.getUser(),
                ReportStatus.ACCEPTED
        )).thenReturn("announces");

        this.mvc.perform(post("/manager/reports/{id}/accept", id)
                .with(csrf())
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manager/reports/announces"));

        verify(reportService).action(
                id,
                this.mockAuthContext.getUser(),
                ReportStatus.ACCEPTED
        );
    }

    @Test
    public void testPostReportReject() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        Long id = 1L;

        when(this.reportService.action(
                id,
                this.mockAuthContext.getUser(),
                ReportStatus.REJECTED
        )).thenReturn("announces");

        this.mvc.perform(post("/manager/reports/{id}/reject", id)
                .with(csrf())
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manager/reports/announces"));

        verify(reportService).action(
                id,
                this.mockAuthContext.getUser(),
                ReportStatus.REJECTED
        );
    }
}
