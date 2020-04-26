package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AnnounceRepository extends PagingAndSortingRepository<Announce, Long> {

    Page<Announce> findAll(Example<Announce> example, Pageable page);
    List<Announce> findAllByOrderByDateDesc();
    Page<Announce> findAllByStatusAndUserOrderByDateDesc(AnnounceStatus status, User user, Pageable page);
    Long countAllByStatusAndUser(AnnounceStatus status, User user);
}
