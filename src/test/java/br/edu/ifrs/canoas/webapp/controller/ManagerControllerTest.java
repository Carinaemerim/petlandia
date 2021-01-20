package br.edu.ifrs.canoas.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest extends BaseTest {
    @Test
    public void testIndexGet() {
        fail("Not implemented");
    }
}
