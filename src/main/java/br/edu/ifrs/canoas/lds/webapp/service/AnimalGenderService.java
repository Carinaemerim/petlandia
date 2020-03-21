package br.edu.ifrs.canoas.lds.webapp.service;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalGender;
import br.edu.ifrs.canoas.lds.webapp.repository.AnimalGenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalGenderService {

    private final AnimalGenderRepository animalGenderdRepository;

    public List<AnimalGender> listAnimalGender(){
        return animalGenderdRepository.findAllByOrderByNameAsc();
    }

}
