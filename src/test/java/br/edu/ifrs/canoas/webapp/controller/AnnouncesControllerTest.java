package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.Comment;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.helper.CommentHelper;
import br.edu.ifrs.canoas.webapp.helper.PaginatedEntityHelper;
import br.edu.ifrs.canoas.webapp.service.AnnounceListService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnouncesController.class)
public class AnnouncesControllerTest extends BaseTest {
    @MockBean
    AnnounceListService announceListService;

    @Test
    public void testGetListGet() throws Exception {
        this.mockAuthContext.mockAuthUser();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
                );

        when(announceService.findAll(
                any(AnnounceFilterForm.class),
                eq(AnnounceStatus.ACTIVE),
                eq(10))
        ).thenReturn(announces);

        this.mvc.perform(get("/announces")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announces", is(announces)))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attributeExists("filters"))
                .andExpect(view().name("/announce/list"));

        verify(announceListService, atMostOnce()).getFilters();
        verify(announceService, atLeastOnce()).findAll(
                any(AnnounceFilterForm.class),
                eq(AnnounceStatus.ACTIVE),
                eq(10)
                );
    }

    @Test
    public void testGetListGetWithForm() throws Exception {
        this.mockAuthContext.mockAuthUser();

        PaginatedEntity<Announce> announces = PaginatedEntityHelper.createPaginatedEntity(
                Announce.class,
                AnnounceHelper.class.getMethod("createAnnounce"),
                null,
                1,
                10
        );

        AnnounceFilterForm filters = new AnnounceFilterForm();
        filters.setAnimalAge(List.of(101L, 102L));

        when(announceService.findAll(
                eq(filters),
                eq(AnnounceStatus.ACTIVE),
                eq(10))
        ).thenReturn(announces);

        this.mvc.perform(get("/announces")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("announceFilterForm", filters))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announces", is(announces)))
                .andExpect(model().attribute("form", is(filters)))
                .andExpect(model().attributeExists("filters"))
                .andExpect(view().name("/announce/list"));

        verify(announceListService, atMostOnce()).getFilters();
        verify(announceService, atLeastOnce()).findAll(
                eq(filters),
                eq(AnnounceStatus.ACTIVE),
                eq(10)
        );
    }

    @Test
    public void testAnnounceDetailsGetAnonymous() throws Exception {
        this.mockAuthContext.mockAuthAnonymous();

        Announce announce = AnnounceHelper.createAnnounce();
        PaginatedEntity<Comment> comments = PaginatedEntityHelper.createPaginatedEntity(
                Comment.class,
                CommentHelper.class.getMethod("createComment"),
                null,
                1,
                10
        );

        Long announceId = 101L;

        when(announceService.findByIdAndStatusActive(
                eq(announceId))
        ).thenReturn(announce);

        when(commentService.findAll(0, 10, announce, CommentStatus.ACTIVE))
                .thenReturn(comments);

        this.mvc.perform(get("/announces/{id}", announceId)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .header("Referer", "http://localhost/users?username=carina")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("comments", is(comments)))
                .andExpect(model().attribute("announce", is(announce)))
                .andExpect(model().attribute("currentUserId", is(0L)))
                .andExpect(model().attribute("isAdmin", is(false)))
                .andExpect(model().attribute("referrer", is("/users?username=carina")))
                .andExpect(view().name("/announce/announceDetails"));

        verify(announceService, atMostOnce()).findByIdAndStatusActive(eq(announceId));
        verify(commentService, atLeastOnce()).findAll(
                eq(0),
                eq(10),
                eq(announce),
                eq(CommentStatus.ACTIVE)
        );

    }

    @Test
    public void testAnnounceDetailsGetAdmin() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();
        PaginatedEntity<Comment> comments = PaginatedEntityHelper.createPaginatedEntity(
                Comment.class,
                CommentHelper.class.getMethod("createComment"),
                null,
                1,
                10
        );

        Long announceId = 101L;

        when(announceService.findByIdAndStatusActive(
                eq(announceId))
        ).thenReturn(announce);

        when(commentService.findAll(5, 10, announce, CommentStatus.ACTIVE))
                .thenReturn(comments);

        this.mvc.perform(get("/announces/{id}", announceId)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .param("page", "5")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("comments", is(comments)))
                .andExpect(model().attribute("announce", is(announce)))
                .andExpect(model().attribute("currentUserId", is(1000L)))
                .andExpect(model().attribute("isAdmin", is(true)))
                .andExpect(model().attribute("referrer", is("/announces")))
                .andExpect(view().name("/announce/announceDetails"));

        verify(announceService, atMostOnce()).findByIdAndStatusActive(eq(announceId));
        verify(commentService, atLeastOnce()).findAll(
                eq(5),
                eq(10),
                eq(announce),
                eq(CommentStatus.ACTIVE)
        );
    }

    @Test
    public void testAnnounceDetailsGetNotFound() throws Exception {
        this.mockAuthContext.mockAuthAnonymous();

        Long announceId = 101L;

        when(announceService.findByIdAndStatusActive(
                eq(announceId))
        ).thenThrow(AnnounceNotFoundException.class);

        this.mvc.perform(get("/announces/{id}", announceId)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());

        verify(announceService, atMostOnce()).findByIdAndStatusActive(eq(announceId));
        verify(commentService, never()).findAll(
                anyInt(),
                anyInt(),
                any(Announce.class),
                any(CommentStatus.class)
        );
    }
}
