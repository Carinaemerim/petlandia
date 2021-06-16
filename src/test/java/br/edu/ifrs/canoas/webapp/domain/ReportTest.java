package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.helper.ReportHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportTest extends BaseTest<Report> {
    @Test
    public void testValidReport() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        violations = this.validator.validate(report);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidReportNullOptionals() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setRatedAt(null);
        report.setRatedBy(null);
        report.setComment(null);

        violations = this.validator.validate(report);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNullStatus() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setStatus(null);

        violations = this.validator.validate(report);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullReportBy() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setReportBy(null);

        violations = this.validator.validate(report);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullMessage() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setMessage(null);

        violations = this.validator.validate(report);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankMessage() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setMessage("");

        violations = this.validator.validate(report);

        this.assertHasViolation("validation.report.message.size", violations);
    }

    @Test
    public void testMinLengthMessage() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setMessage(RandomStringUtils.randomAlphabetic(4));

        violations = this.validator.validate(report);

        this.assertHasViolation("validation.report.message.size", violations);
    }

    @Test
    public void testMaxLengthMessage() {
        Report report = ReportHelper.createReport();
        Set<ConstraintViolation<Report>> violations;

        report.setMessage(RandomStringUtils.randomAlphabetic(251));

        violations = this.validator.validate(report);

        this.assertHasViolation("validation.report.message.size", violations);
    }
}
