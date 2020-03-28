package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.AnimalAge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalAgeRepository extends JpaRepository<AnimalAge, Long> {
    List<AnimalAge> findAllByOrderByIdAsc();

}
