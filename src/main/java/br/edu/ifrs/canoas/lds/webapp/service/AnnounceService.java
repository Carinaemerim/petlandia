package br.edu.ifrs.canoas.lds.webapp.service;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import br.edu.ifrs.canoas.lds.webapp.domain.City;
import br.edu.ifrs.canoas.lds.webapp.repository.AnimalTypeRepository;
import br.edu.ifrs.canoas.lds.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.lds.webapp.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class AnnounceService {

    private static final int PAGE_LENGTH = 3;

    private final AnnounceRepository announceRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final CityRepository cityRepository;

    public Page<Announce> findAll(int pageNumber){

        pageNumber -= 1;

        if(pageNumber < 0){
            pageNumber = 0;
        }

        //TODO RNG03
        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH, Sort.by("date").descending());
        return announceRepository.findAll(page);

    }

    //TODO RNG02
    public List<AnimalType> getAnimalTypes(){
        return animalTypeRepository.findAllByOrderByNameAsc();
    }

    public Announce findById(Long id){
        return announceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    //TODO RNG01
    public List<City> getCityTypes() { return cityRepository.findAllByOrderByDescriptionAsc(); }


}
