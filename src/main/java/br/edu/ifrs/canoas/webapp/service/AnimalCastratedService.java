package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalCastrated;
import br.edu.ifrs.canoas.webapp.repository.AnimalCastratedRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimalCastratedService {

    private final AnimalCastratedRepository animalCastratedRepository;

    public List<AnimalCastrated> listAnimalCastrated() {
        return animalCastratedRepository.findAllByOrderByNameAsc();
    }

}
