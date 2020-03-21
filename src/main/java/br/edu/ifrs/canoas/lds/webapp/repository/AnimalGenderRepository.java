package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalGender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalGenderRepository extends JpaRepository<AnimalGender, Long> {
    List<AnimalGender> findAllByOrderByNameAsc();

}
