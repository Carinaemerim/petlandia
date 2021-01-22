package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.helper.ScoreHelper;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AnimalAge é um label de score. Ao testar AnimalAge testamos score
 */
public class ScoreTest extends BaseTest<AnimalAge> {
    @Test
    public void testValidScore() {
        AnimalAge score = ScoreHelper.createScore("age", 5, AnimalAge.class);
        Set<ConstraintViolation<AnimalAge>> violations;

        violations = this.validator.validate(score);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNullName() {
        AnimalAge score = ScoreHelper.createScore("age", 5, AnimalAge.class);
        Set<ConstraintViolation<AnimalAge>> violations;

        score.setName(null);

        violations = this.validator.validate(score);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankName() {
        AnimalAge score = ScoreHelper.createScore("age", 5, AnimalAge.class);
        Set<ConstraintViolation<AnimalAge>> violations;

        score.setName("");

        violations = this.validator.validate(score);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullLabel() {
        AnimalAge score = ScoreHelper.createScore("age", 5, AnimalAge.class);
        Set<ConstraintViolation<AnimalAge>> violations;

        score.setLabel(null);

        violations = this.validator.validate(score);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankLabel() {
        AnimalAge score = ScoreHelper.createScore("age", 5, AnimalAge.class);
        Set<ConstraintViolation<AnimalAge>> violations;

        score.setLabel("");

        violations = this.validator.validate(score);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullWeight() {
        AnimalAge score = ScoreHelper.createScore("age", 5, AnimalAge.class);
        Set<ConstraintViolation<AnimalAge>> violations;

        score.setWeight(null);

        violations = this.validator.validate(score);

        this.assertHasViolation("field.required", violations);
    }
}
