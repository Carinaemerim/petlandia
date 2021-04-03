package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.forms.AnnounceCreateFrom;
import br.edu.ifrs.canoas.webapp.helper.*;
import br.edu.ifrs.canoas.webapp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnounceController.class)
public class AnnounceControllerTest extends BaseTest {
    @MockBean
    ReportService reportService;
    @MockBean
    AnimalCastratedService animalCastratedService;
    @MockBean
    AnimalGenderService animalGenderService;
    @MockBean
    AnimalSizeService animalSizeService;
    @MockBean
    AnimalTypeService animalTypeService;
    @MockBean
    AnimalAgeService animalAgeService;
    @MockBean
    AnimalColorService animalColorService;

    private final Dimension imageTarget = new Dimension(512, 288);

    @Test
    public void testCreateGet() throws Exception {
        this.mockAuthContext.mockAuthUser();

        Map<String, List<? extends Score>> scoresMap = ScoreHelper.createScoresMap();

        this.mockScoreServices(scoresMap);

        this.mvc.perform(get("/announce/create")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("animalCastrated", is(scoresMap.get("animalCastrateds"))))
                .andExpect(model().attribute("animalGender", is(scoresMap.get("animalGenders"))))
                .andExpect(model().attribute("animalSize", is(scoresMap.get("animalSizes"))))
                .andExpect(model().attribute("animalType", is(scoresMap.get("animalTypes"))))
                .andExpect(model().attribute("animalAges", is(scoresMap.get("animalAges"))))
                .andExpect(model().attribute("animalColors", is(scoresMap.get("animalColors"))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalCastrated", is(scoresMap.get("animalCastrateds").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalGender", is(scoresMap.get("animalGenders").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalSize", is(scoresMap.get("animalSizes").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalAge", is(scoresMap.get("animalAges").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalColor", is(scoresMap.get("animalColors").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalColor", is(scoresMap.get("animalColors").get(0))))))
                .andExpect(model().attribute("form", hasProperty("mainPhotoCropper",
                        hasProperty("required", is(true)))));

        verify(animalAgeService, times(1)).listAnimalAge();
        verify(animalColorService, times(1)).listAnimalColor();
        verify(animalCastratedService, times(1)).listAnimalCastrated();
        verify(animalTypeService, times(1)).listAnimalType();
        verify(animalSizeService, times(1)).listAnimalSize();
        verify(animalGenderService, times(1)).listAnimalGender();
    }

    @Test
    public void testCreatePost() throws Exception {
        this.mockAuthContext.mockAuthUser();
        Map<String, List<? extends Score>> scoresMap = ScoreHelper.createScoresMap();
        this.mockScoreServices(scoresMap);

        AnnounceCreateFrom form = AnnounceFormHelper.createForm();
        form.getAnnounce().setId(100L);

        when(announceService.save(any(Announce.class))).thenReturn(form.getAnnounce());

        this.mvc.perform(post("/announce/create")
                .with(csrf())
                .flashAttr("form", form)
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "announce.form.success"))
                .andExpect(view().name("redirect:/announces/" + form.getAnnounce().getId()));

        verify(animalAgeService, times(1)).listAnimalAge();
        verify(animalColorService, times(1)).listAnimalColor();
        verify(animalCastratedService, times(1)).listAnimalCastrated();
        verify(animalTypeService, times(1)).listAnimalType();
        verify(animalSizeService, times(1)).listAnimalSize();
        verify(animalGenderService, times(1)).listAnimalGender();

        verify(announceService, times(1)).save(eq(form.getAnnounce()));

        assertThat(form.getAnnounce().getUser().getId()).isEqualTo(this.mockAuthContext.getUser().getId());
        assertThat(form.getAnnounce().getStatus()).isEqualTo(AnnounceStatus.ACTIVE);
        assertThat(form.getAnnounce().getMainPhoto()).isNotBlank();
    }

    @Test
    public void testEditGet() throws Exception {
        this.mockAuthContext.mockAuthUser();
        Map<String, List<? extends Score>> scoresMap = ScoreHelper.createScoresMap();
        this.mockScoreServices(scoresMap);

        AnnounceCreateFrom form = AnnounceFormHelper.editForm();
        form.getAnnounce().setUser(this.mockAuthContext.getUser());

        when(announceService.findById(eq(form.getAnnounce().getId()))).thenReturn(form.getAnnounce());

        this.mvc.perform(get("/announce/{id}/edit", form.getAnnounce().getId())
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("form", is(form)))
                .andExpect(model().attribute("animalCastrated", is(scoresMap.get("animalCastrateds"))))
                .andExpect(model().attribute("animalGender", is(scoresMap.get("animalGenders"))))
                .andExpect(model().attribute("animalSize", is(scoresMap.get("animalSizes"))))
                .andExpect(model().attribute("animalType", is(scoresMap.get("animalTypes"))))
                .andExpect(model().attribute("animalAges", is(scoresMap.get("animalAges"))))
                .andExpect(model().attribute("animalColors", is(scoresMap.get("animalColors"))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalCastrated", is(scoresMap.get("animalCastrateds").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalGender", is(scoresMap.get("animalGenders").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalSize", is(scoresMap.get("animalSizes").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalAge", is(scoresMap.get("animalAges").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalColor", is(scoresMap.get("animalColors").get(0))))))
                .andExpect(model().attribute("form", hasProperty("announce",
                        hasProperty("animalColor", is(scoresMap.get("animalColors").get(0))))))
                .andExpect(model().attribute("form", hasProperty("mainPhotoCropper",
                        hasProperty("required", is(true)))));

        verify(animalAgeService, times(1)).listAnimalAge();
        verify(animalColorService, times(1)).listAnimalColor();
        verify(animalCastratedService, times(1)).listAnimalCastrated();
        verify(animalTypeService, times(1)).listAnimalType();
        verify(animalSizeService, times(1)).listAnimalSize();
        verify(animalGenderService, times(1)).listAnimalGender();
    }

    @Test
    public void testEditPost() throws Exception {
        this.mockAuthContext.mockAuthUser();
        Map<String, List<? extends Score>> scoresMap = ScoreHelper.createScoresMap();
        this.mockScoreServices(scoresMap);

        AnnounceCreateFrom form = AnnounceFormHelper.editForm();
        form.getAnnounce().setUser(this.mockAuthContext.getUser());

        when(announceService.findById(eq(form.getAnnounce().getId()))).thenReturn(form.getAnnounce());

        this.mvc.perform(post("/announce/{id}/edit", form.getAnnounce().getId())
                .with(csrf())
                .flashAttr("form", form)
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "announce.form.updated"))
                .andExpect(view().name("redirect:/announces/" + form.getAnnounce().getId()));

        verify(announceService, times(1)).save(eq(form.getAnnounce()));
    }

    @Test
    public void testPostToReport() throws Exception {
        this.mockAuthContext.mockAuthUser();
        Announce announce = AnnounceHelper.createAnnounce();
        announce.setId(100L);
        String message = "report test on announce id 100";

        when(announceService.findByIdAndStatusActive(eq(announce.getId()))).thenReturn(announce);

        this.mvc.perform(post("/announce/{id}/report", announce.getId())
                .with(csrf())
                .flashAttr("message", message)
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/announces"));

        verify(reportService, times(1)).save(eq(announce),
                eq(this.mockAuthContext.getUser()), eq(message));
        verify(announceService, times(1)).setStatus(eq(announce),
                eq(AnnounceStatus.WAITING_REVIEW));
    }

    @Test
    public void testSaveComment() throws Exception {
        this.mockAuthContext.mockAuthUser();

        String message = "comment test on announce id 100";

        Announce announce = AnnounceHelper.createAnnounce();
        announce.setId(100L);

        Comment comment = CommentHelper.createComment();
        comment.setUser(this.mockAuthContext.getUser());
        comment.setAnnounce(announce);
        comment.setMessage(message);
        comment.setCreatedAt(null);
        comment.setUpdatedAt(null);

        when(announceService.findByIdAndStatusActive(eq(announce.getId()))).thenReturn(announce);

        this.mvc.perform(post("/announce/{id}/comment", announce.getId())
                .with(csrf())
                .flashAttr("message", message)
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "announce.comment.created"))
                .andExpect(view().name("redirect:/announces/" + announce.getId()));

        verify(commentService, times(1)).save(eq(comment));
    }

    @Test
    public void testPostCommentToReport() throws Exception { fail("not implemented"); }

    @Test
    public void testPostCommentToRemove() throws Exception { fail("not implemented"); }

    @Test
    public void testRemoveAnnounce() throws Exception { fail("not implemented"); }

    @SuppressWarnings("unchecked")
    private void mockScoreServices(Map<String, List<? extends Score>> scoresMap) {
        when(animalTypeService.listAnimalType()).thenReturn((List<AnimalType>) scoresMap.get("animalTypes"));
        when(animalCastratedService.listAnimalCastrated()).thenReturn((List<AnimalCastrated>) scoresMap.get("animalCastrateds"));
        when(animalGenderService.listAnimalGender()).thenReturn((List<AnimalGender>) scoresMap.get("animalGenders"));
        when(animalAgeService.listAnimalAge()).thenReturn((List<AnimalAge>) scoresMap.get("animalAges"));
        when(animalColorService.listAnimalColor()).thenReturn((List<AnimalColor>) scoresMap.get("animalColors"));
        when(animalSizeService.listAnimalSize()).thenReturn((List<AnimalSize>) scoresMap.get("animalSizes"));
    }
}
