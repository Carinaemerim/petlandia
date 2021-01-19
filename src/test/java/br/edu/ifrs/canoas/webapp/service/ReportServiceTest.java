package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.CommentNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.ReportNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.webapp.repository.CommentRepository;
import br.edu.ifrs.canoas.webapp.repository.ReportRepository;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ReportServiceTest extends BaseTest {
    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    AnnounceRepository announceRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveReportAnnounce() {
        Announce announce = announceRepository.findById(101L).get();
        User user = userRepository.findById(101L).get();
        String message = "Mensagem entre 10 e 250 caracteres";

        Report report = this.reportService.save(announce, user, message);

        assertThat(report.getAnnounce()).isEqualTo(announce);
        assertThat(report.getReportBy()).isEqualTo(user);
        assertThat(report.getComment()).isNull();
        assertThat(report.getMessage()).isEqualTo(message);
        assertThat(report.getStatus()).isEqualTo(ReportStatus.WAITING_REVIEW);
        assertThat(report.getRatedBy()).isNull();
        assertThat(report.getRatedAt()).isNull();
    }

    @Test
    public void testSaveReportAnnounceNullAnnounce() {
        User user = userRepository.findById(101L).get();
        String message = "Mensagem entre 10 e 250 caracteres";

        assertThatExceptionOfType(AnnounceNotFoundException.class)
                .isThrownBy(() -> {
                    this.reportService.save((Announce) null, user, message);
                });
    }

    @Test
    public void testSaveReportAnnounceNullUser() {
        Announce announce = announceRepository.findById(101L).get();
        String message = "Mensagem entre 10 e 250 caracteres";

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> {
                    this.reportService.save(announce, null, message);
                });
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveReportComment() {
        Comment comment = commentRepository.findById(101L).get();
        User user = userRepository.findById(101L).get();
        String message = "Mensagem entre 10 e 250 caracteres";

        Report report = this.reportService.save(comment, user, message);

        assertThat(report.getAnnounce()).isEqualTo(comment.getAnnounce());
        assertThat(report.getReportBy()).isEqualTo(user);
        assertThat(report.getComment()).isEqualTo(comment);
        assertThat(report.getMessage()).isEqualTo(message);
        assertThat(report.getStatus()).isEqualTo(ReportStatus.WAITING_REVIEW);
        assertThat(report.getRatedBy()).isNull();
        assertThat(report.getRatedAt()).isNull();
    }

    @Test
    public void testSaveReportCommentNullComment() {
        User user = userRepository.findById(101L).get();
        String message = "Mensagem entre 10 e 250 caracteres";

        assertThatExceptionOfType(CommentNotFoundException.class)
                .isThrownBy(() -> {
                    this.reportService.save((Comment) null, user, message);
                });
    }

    @Test
    public void testSaveReportCommentNullUser() {
        Comment comment = commentRepository.findById(101L).get();
        String message = "Mensagem entre 10 e 250 caracteres";

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> {
                    this.reportService.save(comment, null, message);
                });
    }

    @Test
    public void testFindAllCommentReports() {
        PaginatedEntity<Report> reports;

        reports = this.reportService.findAllComments(0, 8, ReportStatus.WAITING_REVIEW);

        assertThat(reports.getPageLength()).isEqualTo(8);
        assertThat(reports.getCurrentPage()).isEqualTo(0);
        assertThat(reports.getTotalPages()).isEqualTo(0);
        assertThat(reports.getTotalResults()).isEqualTo(1);
        assertThat(reports.getData()).hasSize(1);

        assertThat(reports.getData().stream()
                .allMatch((e) -> e.getStatus().equals(ReportStatus.WAITING_REVIEW) && e.getComment() != null)
        ).isTrue();

        assertThat(reports.getData().get(0).getId()).isEqualTo(102);
    }

    @Test
    public void testFindAllAnnounceReports() {
        PaginatedEntity<Report> reports;

        reports = this.reportService.findAllAnnounces(0, 8, ReportStatus.WAITING_REVIEW);

        assertThat(reports.getPageLength()).isEqualTo(8);
        assertThat(reports.getCurrentPage()).isEqualTo(0);
        assertThat(reports.getTotalPages()).isEqualTo(0);
        assertThat(reports.getTotalResults()).isEqualTo(1);
        assertThat(reports.getData()).hasSize(1);

        assertThat(reports.getData().stream()
                .allMatch((e) -> e.getStatus().equals(ReportStatus.WAITING_REVIEW) && e.getComment() == null)
        ).isTrue();

        assertThat(reports.getData().get(0).getId()).isEqualTo(101);
    }

    @Test
    public void testActionNullReport() {
        User user = userRepository.findById(101L).get();
        Long id = 1030000L;

        assertThatExceptionOfType(ReportNotFoundException.class)
                .isThrownBy(() -> {
                    this.reportService.action(id, user, ReportStatus.ACCEPTED);
                });
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testReportAnnounceAccept() {
        User user = userRepository.findById(101L).get();
        Long id = 101L;

        Report report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.WAITING_REVIEW);
        assertThat(report.getRatedAt()).isNull();
        assertThat(report.getRatedBy()).isNull();
        assertThat(report.getAnnounce().getStatus()).isEqualTo(AnnounceStatus.WAITING_REVIEW);

        String result = this.reportService.action(id, user, ReportStatus.ACCEPTED);

        assertThat(result).isEqualTo("announces");

        report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.ACCEPTED);
        assertThat(report.getRatedAt()).isNotNull();
        assertThat(report.getRatedBy()).isEqualTo(user);
        assertThat(report.getAnnounce().getStatus()).isEqualTo(AnnounceStatus.INACTIVE);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testReportAnnounceReject() {
        User user = userRepository.findById(101L).get();
        Long id = 101L;

        Report report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.WAITING_REVIEW);
        assertThat(report.getRatedAt()).isNull();
        assertThat(report.getRatedBy()).isNull();
        assertThat(report.getAnnounce().getStatus()).isEqualTo(AnnounceStatus.WAITING_REVIEW);

        String result = this.reportService.action(id, user, ReportStatus.REJECTED);

        assertThat(result).isEqualTo("announces");

        report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.REJECTED);
        assertThat(report.getRatedAt()).isNotNull();
        assertThat(report.getRatedBy()).isEqualTo(user);
        assertThat(report.getAnnounce().getStatus()).isEqualTo(AnnounceStatus.ACTIVE);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testReportCommentAccept() {
        User user = userRepository.findById(101L).get();
        Long id = 102L;

        Report report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.WAITING_REVIEW);
        assertThat(report.getRatedAt()).isNull();
        assertThat(report.getRatedBy()).isNull();
        assertThat(report.getComment().getStatus()).isEqualTo(CommentStatus.WAITING_REVIEW);

        String result = this.reportService.action(id, user, ReportStatus.ACCEPTED);

        assertThat(result).isEqualTo("comments");

        report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.ACCEPTED);
        assertThat(report.getRatedAt()).isNotNull();
        assertThat(report.getRatedBy()).isEqualTo(user);
        assertThat(report.getComment().getStatus()).isEqualTo(CommentStatus.DELETED);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testReportCommentReject() {
        User user = userRepository.findById(101L).get();
        Long id = 102L;

        Report report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.WAITING_REVIEW);
        assertThat(report.getRatedAt()).isNull();
        assertThat(report.getRatedBy()).isNull();
        assertThat(report.getComment().getStatus()).isEqualTo(CommentStatus.WAITING_REVIEW);

        String result = this.reportService.action(id, user, ReportStatus.REJECTED);

        assertThat(result).isEqualTo("comments");

        report = reportRepository.findById(id).get();

        assertThat(report.getStatus()).isEqualTo(ReportStatus.REJECTED);
        assertThat(report.getRatedAt()).isNotNull();
        assertThat(report.getRatedBy()).isEqualTo(user);
        assertThat(report.getComment().getStatus()).isEqualTo(CommentStatus.ACTIVE);
    }
}
