package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.Comment;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findAllByAnnounceEqualsOrderByCreatedAtDesc(Pageable page, Announce announce);

    Page<Comment> findAllByAnnounceEqualsAndStatusEqualsOrderByCreatedAtDesc(Pageable page, Announce announce, CommentStatus status);

    Comment findByIdAndAnnounce(Long id, Announce announce);

    Long countAllByStatus(CommentStatus status);
}