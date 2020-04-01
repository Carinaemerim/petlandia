package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.AnimalColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalColorRepository extends JpaRepository<AnimalColor, Long> {
    List<AnimalColor> findAllByOrderByIdAsc();

}
