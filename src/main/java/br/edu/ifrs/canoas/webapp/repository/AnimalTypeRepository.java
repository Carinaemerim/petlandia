package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    List<AnimalType> findAllByOrderByNameAsc();

}
