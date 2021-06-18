package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.forms.UserSummary;
import br.edu.ifrs.canoas.webapp.helper.PaginatedEntityHelper;
import br.edu.ifrs.canoas.webapp.helper.UserHelper;
import br.edu.ifrs.canoas.webapp.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserAdminManagerController.class)
public class UserAdminManagerControllerTest extends BaseTest {

    @MockBean
    ReportService reportService;

    @Test
    public void testGetActive() throws Exception {
        this.mockAuthContext.mockAuthAdmin();

        String term = "";
        PaginatedEntity<User> users = PaginatedEntityHelper.createPaginatedEntity(
                User.class,
                UserHelper.class.getMethod("createUser"),
                null,
                1,
                10
        );

        when(this.userService.findAll(0, 30, term)).thenReturn(users);

        this.mvc.perform(get("/manager/admin/users")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(view().name("/manager/admin/user/list"));

        verify(this.userService).findAll(0, 30, term);
    }

    @Test
    public void testView() throws Exception {
        this.mockAuthContext.mockAuthAdmin();
        User user = this.mockAuthContext.getUser();
        Long id = user.getId();
        UserSummary summary = new UserSummary(0L, 0L, 0L, 0L, 0L, 0L);

        when(this.userService.findById(id)).thenReturn(user);
        when(this.userService.getSummary(user)).thenReturn(summary);
        this.mvc.perform(get("/manager/admin/users/{id}", id)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(view().name("/manager/admin/user/view"))
                .andExpect(model().attribute("isSelf", is(true)))
                .andExpect(model().attribute("cantReport", is(true)))
                .andExpect(model().attribute("user", is(user)))
                .andExpect(model().attribute("summary", is(summary)))
                .andExpect(model().attributeExists("roles"));

        verify(this.userService).findById(id);
    }

    @Test
    public void testChangeRole() throws Exception {
        this.mockAuthContext.mockAuthAdmin();
        User user = UserHelper.createUser(200L);
        user.setRole(Role.ROLE_USER);
        Long id = user.getId();

        when(this.userService.findById(id)).thenReturn(user);

        this.mvc.perform(post("/manager/admin/users/{id}/change-role", id)
                .flashAttr("role", Role.ROLE_ADMIN)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(status().is3xxRedirection());

        verify(this.userService).findById(id);
        assertThat(user.getRole()).isEqualTo(Role.ROLE_ADMIN);

    }

    @Test
    public void testChangeRoleSameUser() throws Exception {
        this.mockAuthContext.mockAuthAdmin();
        User user = this.mockAuthContext.getUser();
        Long id = user.getId();

        this.mvc.perform(post("/manager/admin/users/{id}/change-role", id)
                .flashAttr("role", Role.ROLE_ADMIN)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testBlockUser() throws Exception {
        this.mockAuthContext.mockAuthAdmin();
        User user = UserHelper.createUser(200L);
        Long id = user.getId();

        when(this.userService.findById(id)).thenReturn(user);

        this.mvc.perform(post("/manager/admin/users/{id}/block-user", id)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(status().is3xxRedirection());

        verify(this.userService).findById(id);
        verify(this.userService).delete(user);
    }

    @Test
    public void testBlockUserSameUser() throws Exception {
        this.mockAuthContext.mockAuthAdmin();
        User user = this.mockAuthContext.getUser();
        Long id = user.getId();

        when(this.userService.findById(id)).thenReturn(user);

        this.mvc.perform(post("/manager/admin/users/{id}/block-user", id)
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .characterEncoding("UTF-8"))
                .andExpect(status().isForbidden());

    }
}
