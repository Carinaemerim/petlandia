package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.Comment;
import br.edu.ifrs.canoas.webapp.domain.Report;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends PagingAndSortingRepository<Report, Long> {
    Page<Report> findAllByStatusAndCommentIsNullOrderByCreatedAtDesc(Pageable page, ReportStatus status);
    Page<Report> findAllByStatusAndCommentIsNotNullOrderByCreatedAtDesc(Pageable page, ReportStatus status);
    Report findByIdAndStatusEquals(Long id, ReportStatus status);
}