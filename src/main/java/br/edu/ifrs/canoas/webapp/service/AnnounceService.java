package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.dao.AnnounceDao;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.repository.AnimalTypeRepository;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@AllArgsConstructor
@Service
public class AnnounceService {

    private static final int PAGE_LENGTH = 2;

    private final AnnounceRepository announceRepository;
    private final AnnounceDao announceDao;
    private final AnimalTypeRepository animalTypeRepository;

    public Page<Announce> findAll(int pageNumber, Long cityId, Long animalTypeId){

        pageNumber -= 1;

        if(pageNumber < 0){
            pageNumber = 0;
        }

        Example<Announce> example = buildQuery(cityId, animalTypeId);

        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH, Sort.by("date").descending());
        return announceRepository.findAll(example, page);

    }

    private Example<Announce> buildQuery(Long cityId, Long animalTypeId){
        Announce announce = new Announce();

        if(animalTypeId != null){
            announce.setAnimalType(animalTypeRepository.findById(animalTypeId).orElse(null));
        }

        ExampleMatcher example = ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withMatcher("city", exact())
                .withMatcher("type", exact());

        return Example.of(announce, example);
    }
    public Announce findById(Long id){
        return announceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    public Announce save(Announce announce) {
        return announceRepository.save(announce);
    }

    public List<Announce> listAnnounce() {
        return announceRepository.findAllByOrderByCreatedAtDescIdDesc();
    }

    public PaginatedEntity<Announce> findAll(int pageNumber, User user, AnnounceStatus status){
        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH);
        Page<Announce> announcePage = announceRepository.findAllByStatusAndUserOrderByCreatedAtDescIdDesc(status, user, page);

        return PaginatedEntity.<Announce>builder()
                .currentPage(pageNumber)
                .data(announcePage.getContent())
                .totalResults(announcePage.getTotalElements())
                .pageLength(PAGE_LENGTH)
                .build();
    }

    public Long countAll(User user, AnnounceStatus status){
        return announceRepository.countAllByStatusAndUser(status, user);
    }

    public PaginatedEntity<Announce> findAll(AnnounceFilterForm filters, AnnounceStatus status, int pageLength) {
        Long count = announceDao.countAllByFilter(filters, status);
        List<Announce> list = announceDao.findAllByFilter(filters, status, pageLength);

        return PaginatedEntity.<Announce>builder()
                .currentPage(filters.getPage())
                .data(list)
                .totalResults(count)
                .pageLength(pageLength)
                .build();
    }
}
