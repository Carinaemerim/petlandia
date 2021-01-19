package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.Score;
import br.edu.ifrs.canoas.webapp.forms.FilterForm;
import br.edu.ifrs.canoas.webapp.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnounceListServiceTest extends BaseTest {
    @Autowired
    AnnounceListService announceListService;

    @Autowired
    AnimalAgeRepository animalAgeRepository;

    @Autowired
    AnimalTypeRepository animalTypeRepository;

    @Autowired
    AnimalCastratedRepository animalCastratedRepository;

    @Autowired
    AnimalGenderRepository animalGenderRepository;

    @Autowired
    AnimalSizeRepository animalSizeRepository;

    @Autowired
    AnimalColorRepository animalColorRepository;



    @Test
    public <T extends Score> void testGetFilters() {
        List<FilterForm> filters = announceListService.getFilters();

        this.testFilterType(
                filters.get(0),
                "animalAge",
                messages.get("form.filter.animalAge"),
                animalAgeRepository.findAllByOrderByIdAsc()
        );

        this.testFilterType(
                filters.get(1),
                "animalType",
                messages.get("form.filter.animalType"),
                animalTypeRepository.findAllByOrderByNameAsc()
        );

        this.testFilterType(
                filters.get(2),
                "animalCastrated",
                messages.get("form.filter.animalCastrated"),
                animalCastratedRepository.findAllByOrderByNameAsc()
        );

        this.testFilterType(
                filters.get(3),
                "animalGender",
                messages.get("form.filter.animalGender"),
                animalGenderRepository.findAllByOrderByNameAsc()
        );

        this.testFilterType(
                filters.get(4),
                "animalSize",
                messages.get("form.filter.animalSize"),
                animalSizeRepository.findAllByOrderByNameAsc()
        );

        this.testFilterType(
                filters.get(5),
                "animalColor",
                messages.get("form.filter.animalColor"),
                animalColorRepository.findAllByOrderByIdAsc()
        );
    }

    protected <T extends Score> void testFilterType(
            FilterForm<Score> filter,
            String id,
            String label,
            List<T> options
    ) {
        assertThat(filter.getId()).isEqualTo(id);
        assertThat(filter.getLabel()).isEqualTo(label);
        assertThat(filter.getOptions()).isEqualTo(options);
    }
}
