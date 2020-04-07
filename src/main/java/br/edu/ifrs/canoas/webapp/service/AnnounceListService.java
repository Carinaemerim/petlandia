package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.forms.FilterForm;
import br.edu.ifrs.canoas.webapp.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AnnounceListService {

    private final Messages messages;

    private final AnimalColorRepository animalColorRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalAgeRepository animalAgeRepository;
    private final AnimalGenderRepository animalGenderRepository;
    private final AnimalCastratedRepository animalCastratedRepository;
    private final AnimalSizeRepository animalSizeRepository;

    public List<FilterForm> getFilters() {
        List<FilterForm> filters = new ArrayList<>();
        FilterForm filter = null;

        filter = new FilterForm<AnimalAge>();
        filter.setId("animalAge");
        filter.setLabel(messages.get("form.filter." + filter.getId()));
        filter.setOptions(animalAgeRepository.findAllByOrderByIdAsc());
        filters.add(filter);

        filter = new FilterForm<AnimalType>();
        filter.setId("animalType");
        filter.setLabel(messages.get("form.filter." + filter.getId()));
        filter.setOptions(animalTypeRepository.findAllByOrderByNameAsc());
        filters.add(filter);

        filter = new FilterForm<AnimalCastrated>();
        filter.setId("animalCastrated");
        filter.setLabel(messages.get("form.filter." + filter.getId()));
        filter.setOptions(animalCastratedRepository.findAllByOrderByNameAsc());
        filters.add(filter);

        filter = new FilterForm<AnimalGender>();
        filter.setId("animalGender");
        filter.setLabel(messages.get("form.filter." + filter.getId()));
        filter.setOptions(animalGenderRepository.findAllByOrderByNameAsc());
        filters.add(filter);

        filter = new FilterForm<AnimalSize>();
        filter.setId("animalSize");
        filter.setLabel(messages.get("form.filter." + filter.getId()));
        filter.setOptions(animalSizeRepository.findAllByOrderByNameAsc());
        filters.add(filter);

        filter = new FilterForm<AnimalColor>();
        filter.setId("animalColor");
        filter.setLabel(messages.get("form.filter." + filter.getId()));
        filter.setOptions(animalColorRepository.findAllByOrderByIdAsc());
        filters.add(filter);

        return filters;
    }
}
