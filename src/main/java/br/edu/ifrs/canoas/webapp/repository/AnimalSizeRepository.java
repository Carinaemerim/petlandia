package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.AnimalSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnimalSizeRepository extends JpaRepository<AnimalSize, Long> {
    List<AnimalSize> findAllByOrderByNameAsc();

}
