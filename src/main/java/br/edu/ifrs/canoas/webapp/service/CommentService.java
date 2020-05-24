package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.Comment;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public void save(Comment comment) {
		commentRepository.save(comment);
	}

	public PaginatedEntity<Comment> findAll(int pageNumber, int pageLenght, Announce announce){
		Pageable page = PageRequest.of(pageNumber, pageLenght);
		Page<Comment> pageComment = commentRepository.findAllByAnnounceEqualsOrderByCreatedAtDesc(page, announce);

		return PaginatedEntity.<Comment>builder()
				.currentPage(pageNumber)
				.data(pageComment.getContent())
				.totalResults(pageComment.getTotalElements())
				.pageLength(pageLenght)
				.build();
	}

}
