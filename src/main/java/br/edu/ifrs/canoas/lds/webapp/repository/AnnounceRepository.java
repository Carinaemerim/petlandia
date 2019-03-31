package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnnounceRepository extends PagingAndSortingRepository<Announce, Long> {

    Page<Announce> findAll(Example<Announce> example, Pageable page);
}
