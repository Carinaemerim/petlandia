package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalCastrated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalCastratedRepository extends JpaRepository<AnimalCastrated, Long> {
    List<AnimalCastrated> findAllByOrderByNameAsc();

}
