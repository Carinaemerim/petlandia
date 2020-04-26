package br.edu.ifrs.canoas.webapp.service;

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
import javax.persistence.criteria.*;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@AllArgsConstructor
@Service
public class AnnounceService {

    private static final int PAGE_LENGTH = 2;

    private final AnnounceRepository announceRepository;
    private final AnimalTypeRepository animalTypeRepository;

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

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
        return announceRepository.findAllByOrderByDateDesc();
    }



    public Page<Announce> findAll(int pageNumber, User user, AnnounceStatus status){
        pageNumber -= 1;

        if(pageNumber < 0){
            pageNumber = 0;
        }

        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH);
        return announceRepository.findAllByStatusAndUserOrderByDateDesc(status, user, page);
    }

    public Long countAll(User user, AnnounceStatus status){
        return announceRepository.countAllByStatusAndUser(status, user);
    }

    @SuppressWarnings("unchecked")
    public PaginatedEntity<Announce> findAllByFilter(AnnounceFilterForm filters) {
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Announce> announceCriteriaQuery = criteriaBuilder.createQuery(Announce.class);
            Root<Announce> root = announceCriteriaQuery.from(Announce.class);
            List<Predicate> predicates = new LinkedList<>();

            if (filters.getAnimalCastrated() != null && !filters.getAnimalCastrated().isEmpty()) {
                Expression<Long> e = root.get("animalCastrated");
                predicates.add(e.in(filters.getAnimalCastrated()));
            }

            if (filters.getAnimalColor() != null && !filters.getAnimalColor().isEmpty()) {
                Expression<Long> e = root.get("animalColor");
                predicates.add(e.in(filters.getAnimalColor()));
            }

            if (filters.getAnimalGender() != null && !filters.getAnimalGender().isEmpty()) {
                Expression<Long> e = root.get("animalGender");
                predicates.add(e.in(filters.getAnimalGender()));
            }

            if (filters.getAnimalSize() != null && !filters.getAnimalSize().isEmpty()) {
                Expression<Long> e = root.get("animalSize");
                predicates.add(e.in(filters.getAnimalSize()));
            }

            if (filters.getAnimalType() != null && !filters.getAnimalType().isEmpty()) {
                Expression<Long> e = root.get("animalType");
                predicates.add(e.in(filters.getAnimalType()));
            }

            if (filters.getAnimalAge() != null && !filters.getAnimalAge().isEmpty()) {
                Expression<Long> e = root.get("animalAge");
                predicates.add(e.in(filters.getAnimalAge()));
            }

            predicates.add(criteriaBuilder.equal(root.get("status"), AnnounceStatus.ACTIVE));
            announceCriteriaQuery.where(predicates.toArray(new Predicate[]{}));
            announceCriteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));

            TypedQuery query = entityManager.createQuery(announceCriteriaQuery)
                    .setFirstResult(filters.getPage() * PAGE_LENGTH)
                    .setMaxResults(PAGE_LENGTH);

            List<Announce> list = query.getResultList();

            return PaginatedEntity.<Announce>builder()
                    .currentPage(filters.getPage() - 1)
                    .data(list)
                    .totalResults(query.getMaxResults())
                    .pageLength(PAGE_LENGTH)
                    .build();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
