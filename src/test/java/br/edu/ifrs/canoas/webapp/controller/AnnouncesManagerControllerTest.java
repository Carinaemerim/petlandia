package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.helper.PaginatedEntityHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnouncesManagerController.class)
public class AnnouncesManagerControllerTest extends BaseTest {
    @Test
    public void testGetActiveGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
        );

        User user = this.mockAuthContext.getUser();

        when(announceService.findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.ACTIVE))
        ).thenReturn(announces);

        when(userService.findById(user.getId())).thenReturn(user);

        this.mvc.perform(get("/manager/announces/active")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announces", is(announces)))
                .andExpect(model().attribute("status", is(AnnounceStatus.ACTIVE)))
                .andExpect(view().name("/manager/announces/list"));

        verify(userService, atMostOnce()).findById(eq(user.getId()));
        verify(announceService, atLeastOnce()).findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.ACTIVE)
        );
    }

    @Test
    public void testGetWaitingReviewGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
        );

        User user = this.mockAuthContext.getUser();

        when(announceService.findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.WAITING_REVIEW))
        ).thenReturn(announces);

        when(userService.findById(user.getId())).thenReturn(user);

        this.mvc.perform(get("/manager/announces/waiting-review")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announces", is(announces)))
                .andExpect(model().attribute("status", is(AnnounceStatus.WAITING_REVIEW)))
                .andExpect(view().name("/manager/announces/list"));

        verify(userService, atMostOnce()).findById(eq(user.getId()));
        verify(announceService, atLeastOnce()).findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.WAITING_REVIEW)
        );
    }

    @Test
    public void testGetBlockedGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
        );

        User user = this.mockAuthContext.getUser();

        when(announceService.findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.INACTIVE))
        ).thenReturn(announces);

        when(userService.findById(user.getId())).thenReturn(user);

        this.mvc.perform(get("/manager/announces/blocked")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announces", is(announces)))
                .andExpect(model().attribute("status", is(AnnounceStatus.INACTIVE)))
                .andExpect(view().name("/manager/announces/list"));

        verify(userService, atMostOnce()).findById(eq(user.getId()));
        verify(announceService, atLeastOnce()).findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.INACTIVE)
        );
    }

    @Test
    public void testGetNullUserGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
        );

        User user = this.mockAuthContext.getUser();

        when(announceService.findAll(
                eq(0),
                eq(user),
                eq(AnnounceStatus.ACTIVE))
        ).thenReturn(announces);

        when(userService.findById(user.getId())).thenReturn(null);

        this.mvc.perform(get("/manager/announces/active")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound());

        verify(userService, atMostOnce()).findById(eq(user.getId()));
        verifyNoInteractions(announceService);
    }
}
