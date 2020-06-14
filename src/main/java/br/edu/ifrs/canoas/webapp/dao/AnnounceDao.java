package br.edu.ifrs.canoas.webapp.dao;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
@AllArgsConstructor
@Component
public class AnnounceDao {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public List<Announce> findAllByFilter(AnnounceFilterForm filters, AnnounceStatus status, int pageLength) {
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Announce> announceCriteriaQuery = criteriaBuilder.createQuery(Announce.class);
            Root<Announce> root = announceCriteriaQuery.from(Announce.class);
            List<Predicate> predicates = new LinkedList<>();

            setAnnounceFilters(filters, status, root, criteriaBuilder, predicates);

            announceCriteriaQuery.where(predicates.toArray(new Predicate[]{}));
            announceCriteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt")),
                    criteriaBuilder.desc(root.get("id")));

            TypedQuery query = entityManager.createQuery(announceCriteriaQuery)
                    .setFirstResult(filters.getPage() * pageLength)
                    .setMaxResults(pageLength);

            return query.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Long countAllByFilter(AnnounceFilterForm filters, AnnounceStatus status) {
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> announceCriteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Announce> root = announceCriteriaQuery.from(Announce.class);
            List<Predicate> predicates = new LinkedList<>();

            setAnnounceFilters(filters, status, root, criteriaBuilder, predicates);

            announceCriteriaQuery.where(predicates.toArray(new Predicate[]{}));
            announceCriteriaQuery.select(criteriaBuilder.count(root.get("id")));
            return entityManager.createQuery(announceCriteriaQuery).getSingleResult();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void setAnnounceFilters(AnnounceFilterForm filters, AnnounceStatus status, Root<Announce> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
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

        predicates.add(criteriaBuilder.equal(root.get("status"), status));
    }


}
