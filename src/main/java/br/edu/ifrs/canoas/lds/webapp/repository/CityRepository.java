package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//TODO RGN01
public interface CityRepository extends JpaRepository<City, Long>{
    List<City> findAllByOrderByDescriptionAsc();

}
