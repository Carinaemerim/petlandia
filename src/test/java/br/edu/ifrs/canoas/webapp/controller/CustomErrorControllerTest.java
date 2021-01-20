package br.edu.ifrs.canoas.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(CustomErrorController.class)
public class CustomErrorControllerTest extends BaseTest {

    @Test
    public void testErrorNotFound() {
        fail("Not implemented");
    }

    @Test
    public void testErrorForbidden() {
        fail("Not implemented");
    }

    @Test
    public void testErrorInternal() {
        fail("Not implemented");
    }
}
