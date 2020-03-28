package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalAge;
import br.edu.ifrs.canoas.webapp.repository.AnimalAgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalAgeService {

    private final AnimalAgeRepository animalAgeRepository;

    public List<AnimalAge> listAnimalAge(){
        return animalAgeRepository.findAllByOrderByIdAsc();
    }

}
