package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.domain.validation.UserCreateGroup;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import br.edu.ifrs.canoas.webapp.helper.UserHelper;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest extends BaseTest<User> {
    @Test
    public void testValidUser() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNullUserName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setUsername(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }
}
