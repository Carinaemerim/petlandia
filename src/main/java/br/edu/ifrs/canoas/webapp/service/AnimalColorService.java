package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalAge;
import br.edu.ifrs.canoas.webapp.domain.AnimalColor;
import br.edu.ifrs.canoas.webapp.repository.AnimalAgeRepository;
import br.edu.ifrs.canoas.webapp.repository.AnimalColorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalColorService {

    private final AnimalColorRepository animalColorRepository;

    public List<AnimalColor> listAnimalColor(){
        return animalColorRepository.findAllByOrderByIdAsc();
    }

}
