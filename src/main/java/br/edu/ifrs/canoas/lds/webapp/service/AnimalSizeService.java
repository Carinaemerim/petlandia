package br.edu.ifrs.canoas.lds.webapp.service;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalSize;
import br.edu.ifrs.canoas.lds.webapp.repository.AnimalSizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalSizeService {

    private final AnimalSizeRepository animalSizeRepository;

    public List<AnimalSize> listAnimalCastrated(){
        return animalSizeRepository.findAllByOrderByNameAsc();
    }

}
