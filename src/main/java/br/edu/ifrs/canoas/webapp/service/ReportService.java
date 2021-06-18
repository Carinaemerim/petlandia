package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.*;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.CommentNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.ReportNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.webapp.repository.CommentRepository;
import br.edu.ifrs.canoas.webapp.repository.ReportRepository;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AnnounceRepository announceRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void save(Report report) {
        reportRepository.save(report);
    }

    public Report save(Announce announce, User user, String message) {
        if (announce == null) {
            throw new AnnounceNotFoundException();
        }

        if (user == null) {
            throw new UserNotFoundException();
        }

        Report report = new Report();
        report.setAnnounce(announce);
        report.setReportBy(user);
        report.setType(ReportType.ANNOUNCE);
        report.setMessage(message);
        report.setStatus(ReportStatus.WAITING_REVIEW);
        this.save(report);

        return report;
    }

    public Report save(Comment comment, User user, String message) {
        if (comment == null) {
            throw new CommentNotFoundException();
        }

        if (user == null) {
            throw new UserNotFoundException();
        }

        Report report = new Report();
        report.setAnnounce(comment.getAnnounce());
        report.setReportBy(user);
        report.setType(ReportType.COMMENT);
        report.setMessage(message);
        report.setComment(comment);
        report.setStatus(ReportStatus.WAITING_REVIEW);
        this.save(report);

        return report;
    }

    public Report save(User reported, User user, String message) {
        if (reported == null) {
            throw new UserNotFoundException();
        }

        if (user == null) {
            throw new UserNotFoundException();
        }

        Report report = new Report();
        report.setUser(reported);
        report.setReportBy(user);
        report.setType(ReportType.USER);
        report.setMessage(message);
        report.setStatus(ReportStatus.WAITING_REVIEW);
        this.save(report);

        return report;
    }

    public Report[] findAllByStatusAndTypeAndUser(ReportStatus status, ReportType type, User user) {
        return reportRepository.findAllByStatusAndTypeAndUser(status, type, user);
    }

    public PaginatedEntity<Report> findAllByStatusAndType(int pageNumber, int pageLength, ReportStatus status, ReportType type) {
        Pageable page = PageRequest.of(pageNumber, pageLength);
        Page<Report> reportPage = reportRepository.findAllByStatusAndTypeOrderByCreatedAtDesc(page, status, type);

        return PaginatedEntity.<Report>builder()
                .currentPage(pageNumber)
                .data(reportPage.getContent())
                .totalResults(reportPage.getTotalElements())
                .pageLength(pageLength)
                .build();
    }

    public String action(Long id, User user, ReportStatus status) {
        Report report = this.reportRepository.findByIdAndStatusEquals(id, ReportStatus.WAITING_REVIEW);
        if (report == null) {
            throw new ReportNotFoundException();
        }

        report.setRatedAt(LocalDateTime.now());
        report.setRatedBy(user);
        report.setStatus(status);

        switch (report.getType()) {
            case ANNOUNCE:
                this.setActionAnnounce(report.getAnnounce(), status);
                this.reportRepository.save(report);
                return "announces";
            case COMMENT:
                this.setActionComment(report.getComment(), status);
                this.reportRepository.save(report);
                return "comments";
            case USER:
                this.setActionUser(report.getUser(), status);
                this.reportRepository.save(report);
                return "users";
            default: throw new RuntimeException("report type invalid");
        }
    }

    private void setActionComment(Comment comment, ReportStatus status) {
        if (comment.getStatus().equals(CommentStatus.WAITING_REVIEW)) {
            if (status.equals(ReportStatus.ACCEPTED)) {
                comment.setStatus(CommentStatus.DELETED);
                this.commentRepository.save(comment);
            }

            if (status.equals(ReportStatus.REJECTED)) {
                comment.setStatus(CommentStatus.ACTIVE);
                this.commentRepository.save(comment);
            }
        }
    }

    private void setActionAnnounce(Announce announce, ReportStatus status) {
        if (announce.getStatus().equals(AnnounceStatus.WAITING_REVIEW)) {
            if (status.equals(ReportStatus.ACCEPTED)) {
                announce.setStatus(AnnounceStatus.INACTIVE);
                this.announceRepository.save(announce);
            }

            if (status.equals(ReportStatus.REJECTED)) {
                announce.setStatus(AnnounceStatus.ACTIVE);
                this.announceRepository.save(announce);
            }
        }
    }

    private void setActionUser(User user, ReportStatus status) {
        boolean admin = Auth.hasRole(new Role[]{Role.ROLE_ADMIN});
        if (user.getStatus().equals(UserStatus.WAITING_REVIEW) && admin) {
            if (status.equals(ReportStatus.ACCEPTED)) {
                user.setStatus(UserStatus.ACTIVE);
                this.userRepository.save(user);
            }

            if (status.equals(ReportStatus.REJECTED)) {
                user.setStatus(UserStatus.ACTIVE);
                this.userRepository.save(user);
            }
        }
    }
}