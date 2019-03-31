package br.edu.ifrs.canoas.lds.webapp.service;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import br.edu.ifrs.canoas.lds.webapp.domain.City;
import br.edu.ifrs.canoas.lds.webapp.repository.AnimalTypeRepository;
import br.edu.ifrs.canoas.lds.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.lds.webapp.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@AllArgsConstructor
@Service
public class AnnounceService {

    private static final int PAGE_LENGTH = 3;

    private final AnnounceRepository announceRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final CityRepository cityRepository;

    public Page<Announce> findAll(int pageNumber, Long cityId, Long animalTypeId){

        pageNumber -= 1;

        if(pageNumber < 0){
            pageNumber = 0;
        }

        Example<Announce> example = buildQuery(cityId, animalTypeId);

        //TODO RNG03
        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH, Sort.by("date").descending());
        return announceRepository.findAll(example, page);

    }

    private Example<Announce> buildQuery(Long cityId, Long animalTypeId){
        Announce announce = new Announce();

        if (cityId != null){
            announce.setCity(cityRepository.findById(cityId).orElse(null));
        }

        if(animalTypeId != null){
            announce.setType(animalTypeRepository.findById(animalTypeId).orElse(null));
        }

        ExampleMatcher example = ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withMatcher("city", exact())
                .withMatcher("type", exact());

        return Example.of(announce, example);
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