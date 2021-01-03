package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.MockAuthContext;
import br.edu.ifrs.canoas.webapp.config.Messages;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest<T> {
    @Autowired
    protected MockAuthContext mockAuthContext;

    @Autowired
    protected Validator validator;

    @Autowired
    protected Messages messages;

    protected void assertHasViolation(String message, Set<ConstraintViolation<T>> violations) {
        List<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());

        assertThat(messages).contains(this.messages.get(message));
    }

    @After
    public void tearDown() {
        this.mockAuthContext.tearDown();
    }
}
