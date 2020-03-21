package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnimalSizeRepository extends JpaRepository<AnimalSize, Long> {
    List<AnimalSize> findAllByOrderByNameAsc();

}
