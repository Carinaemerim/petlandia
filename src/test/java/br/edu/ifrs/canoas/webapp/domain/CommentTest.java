package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.helper.CommentHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest extends BaseTest<Comment> {
    @Test
    public void testValidComment() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        violations = this.validator.validate(comment);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNullStatus() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setStatus(null);

        violations = this.validator.validate(comment);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullUser() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setUser(null);

        violations = this.validator.validate(comment);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnnounce() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setAnnounce(null);

        violations = this.validator.validate(comment);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullMessage() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setMessage(null);

        violations = this.validator.validate(comment);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankMessage() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setMessage("");

        violations = this.validator.validate(comment);

        this.assertHasViolation("validation.report.message.size", violations);
    }

    @Test
    public void testMinLengthMessage() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setMessage(RandomStringUtils.randomAlphabetic(4));

        violations = this.validator.validate(comment);

        this.assertHasViolation("validation.comment.message.size", violations);
    }

    @Test
    public void testMaxLengthMessage() {
        Comment comment = CommentHelper.createComment();
        Set<ConstraintViolation<Comment>> violations;

        comment.setMessage(RandomStringUtils.randomAlphabetic(251));

        violations = this.validator.validate(comment);

        this.assertHasViolation("validation.comment.message.size", violations);
    }
}
