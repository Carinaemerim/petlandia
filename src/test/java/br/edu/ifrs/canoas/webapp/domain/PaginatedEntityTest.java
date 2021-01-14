package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.helper.PaginatedEntityHelper;
import br.edu.ifrs.canoas.webapp.helper.UserHelper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginatedEntityTest {
    @Test
    public void testGetTotalPages() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        PaginatedEntity<User> paginatedEntity = PaginatedEntityHelper.createPaginatedEntity(
                User.class,
                UserHelper.class.getMethod("createUser"),
                null,
                10,
                2
        );

        assertThat(paginatedEntity.getTotalPages()).isEqualTo(4);
    }
}
