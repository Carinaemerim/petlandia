package br.edu.ifrs.canoas.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest extends BaseTest {
    @Test
    public void testIndexGet() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        this.mvc.perform(get("/manager")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(view().name("/manager/index"));
    }

    @Test
    public void testIndexGetAnonymous() throws Exception {
        this.mockAuthContext.mockAuthAnonymous();

        this.mvc.perform(get("/manager")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}
