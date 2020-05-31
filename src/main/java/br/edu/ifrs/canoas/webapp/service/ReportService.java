package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.CommentNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;

	public void save(Report report) {
		reportRepository.save(report);
	}

	public void save(Announce announce, User user, String message) {
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
	}

	public void save(Comment comment, User user, String message) {
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
	}

	public PaginatedEntity<Report> findAll(int pageNumber, int pageLenght, ReportStatus status, boolean toggle) {
		Pageable page = PageRequest.of(pageNumber, pageLenght);
		Page<Report> reportPage;

		if (toggle) {
			reportPage = reportRepository.findAllByStatusAndCommentIsNullOrderByCreatedAtDesc(page, status);
		} else {
			reportPage = reportRepository.findAllByStatusAndCommentIsNotNullOrderByCreatedAtDesc(page, status);
		}

		return PaginatedEntity.<Report>builder()
				.currentPage(pageNumber)
				.data(reportPage.getContent())
				.totalResults(reportPage.getTotalElements())
				.pageLength(pageLenght)
				.build();
	}
}