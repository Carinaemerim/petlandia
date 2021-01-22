package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalSize;
import br.edu.ifrs.canoas.webapp.repository.AnimalSizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalSizeService {

    private final AnimalSizeRepository animalSizeRepository;

    public List<AnimalSize> listAnimalSize() {
        return animalSizeRepository.findAllByOrderByNameAsc();
    }

}
