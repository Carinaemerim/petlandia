package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.webapp.repository.AnimalTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    public List<AnimalType> listAnimalType() {
        return animalTypeRepository.findAllByOrderByNameAsc();
    }

}
