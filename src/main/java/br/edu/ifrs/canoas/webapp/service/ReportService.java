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

    public void save(Report report) {
        reportRepository.save(report);
    }

    //TODO renomear para create, pois este método não faz atualização
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
        report.setMessage(message);
        report.setStatus(ReportStatus.WAITING_REVIEW);
        this.save(report);

        return report;
    }

    //TODO renomear para create, pois este método não faz atualização
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
        report.setMessage(message);
        report.setComment(comment);
        report.setStatus(ReportStatus.WAITING_REVIEW);
        this.save(report);

        return report;
    }

    public PaginatedEntity<Report> findAllComments(int pageNumber, int pageLength, ReportStatus status) {
        Pageable page = PageRequest.of(pageNumber, pageLength);
        Page<Report> reportPage = reportRepository.findAllByStatusAndCommentIsNotNullOrderByCreatedAtDesc(page, status);

        return PaginatedEntity.<Report>builder()
                .currentPage(pageNumber)
                .data(reportPage.getContent())
                .totalResults(reportPage.getTotalElements())
                .pageLength(pageLength)
                .build();
    }

    public PaginatedEntity<Report> findAllAnnounces(int pageNumber, int pageLenght, ReportStatus status) {
        Pageable page = PageRequest.of(pageNumber, pageLenght);
        Page<Report> reportPage = reportRepository.findAllByStatusAndCommentIsNullOrderByCreatedAtDesc(page, status);

        return PaginatedEntity.<Report>builder()
                .currentPage(pageNumber)
                .data(reportPage.getContent())
                .totalResults(reportPage.getTotalElements())
                .pageLength(pageLenght)
                .build();
    }


    // TODO: Revisar o fluxo deste método
    public String action(Long id, User user, ReportStatus status) {
        Report report = this.reportRepository.findByIdAndStatusEquals(id, ReportStatus.WAITING_REVIEW);
        if (report == null) {
            throw new ReportNotFoundException();
        }

        String type = report.getComment() == null ? "announces" : "comments";
        report.setRatedAt(LocalDateTime.now());
        report.setRatedBy(user);
        report.setStatus(status);

        if (report.getComment() != null) {
            this.setActionComment(report.getComment(), status);
        } else {
            this.setActionAnnounce(report.getAnnounce(), status);
        }

        this.reportRepository.save(report);
        return type;
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
}