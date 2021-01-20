package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(UserAdminManagerController.class)
public class UserAdminManagerControllerTest extends BaseTest {
    @MockBean
    UserService userService;

    @Test
    public void testGetActiveGet() {
        fail("Not Implemented");
    }
}
