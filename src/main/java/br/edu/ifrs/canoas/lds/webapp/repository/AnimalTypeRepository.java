package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//TODO RNG02
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    List<AnimalType> findAllByOrderByNameAsc();

}