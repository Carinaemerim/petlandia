package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.AnnounceSuggested;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnnounceRepository extends PagingAndSortingRepository<Announce, Long> {

    default Optional<Announce> findAllByIdAndStatusActive(Long id) {
        return this.findByIdAndStatus(id, AnnounceStatus.ACTIVE);
    }

    Optional<Announce> findByIdAndStatus(Long id, AnnounceStatus status);

    Page<Announce> findAll(Example<Announce> example, Pageable page);

    List<Announce> findAllByOrderByCreatedAtDescIdDesc();

    List<Announce> findFirst5ByStatusEqualsOrderByCreatedAtDescIdDesc(AnnounceStatus status);

    Page<Announce> findAllByStatusAndUserOrderByCreatedAtDescIdDesc(AnnounceStatus status, User user, Pageable page);

    Page<Announce> findAllByStatusOrderByCreatedAtDescIdDesc(AnnounceStatus status, Pageable page);

    Long countAllByStatusAndUser(AnnounceStatus status, User user);

    Long countAllByStatus(AnnounceStatus status);

    /*
     * Devido a um bug no spring, este método retorna uma lista e,
     * a query de count é feita em outro método. Caso seja corrigido, alterar o retorno
     * para Page ao invés de List e remover o método de contagem abaixo
     *
     * Apenas anúncios que não foram criados pelo usuário que esta logado
     */
    @Query(value = "" +
            "SELECT " +
            "   a.id, " +
            "   POWER(" +
            "         POWER(ag.weight - :#{#user.animalAge.weight}, 2) + " +
            "         POWER(ac.weight - :#{#user.animalCastrated.weight}, 2) + " +
            "         POWER(aco.weight - :#{#user.animalColor.weight}, 2) + " +
            "         POWER(age.weight - :#{#user.animalGender.weight}, 2) + " +
            "         POWER(asi.weight - :#{#user.animalSize.weight}, 2) " +
            "   ,0.5) AS score " +
            "FROM announce a " +
            "JOIN animal_age ag ON ag.id = a.animal_age_id " +
            "JOIN animal_castrated ac ON ac.id = a.animal_castrated_id " +
            "JOIN animal_color aco ON aco.id = a.animal_color_id " +
            "JOIN animal_gender age ON age.id = a.animal_gender_id " +
            "JOIN animal_size asi ON asi.id = a.animal_size_id " +
            "WHERE a.animal_type_id = :#{#user.animalType.id} " +
            "AND a.status = :#{#status.name()} " +
            "AND a.user_id != :#{#user.id} " +
            "ORDER BY score ASC",
            nativeQuery = true,
            countQuery = "" +
                    "SELECT COUNT(*) " +
                    "FROM announce a " +
                    "JOIN animal_age ag ON ag.id = a.animal_age_id " +
                    "JOIN animal_castrated ac ON ac.id = a.animal_castrated_id " +
                    "JOIN animal_color aco ON aco.id = a.animal_color_id " +
                    "JOIN animal_gender age ON age.id = a.animal_gender_id " +
                    "JOIN animal_size asi ON asi.id = a.animal_size_id " +
                    "WHERE a.animal_type_id = :#{#user.animalType.id} " +
                    "AND a.status = :#{#status.name()} " +
                    "AND a.user_id != :#{#user.id} ")
    List<AnnounceSuggested> findAllSuggestedByUser(@Param("user") User user, @Param("status") AnnounceStatus status, Pageable pageable);

    @Query(value = "" +
            "SELECT COUNT(*) " +
            "FROM announce a " +
            "JOIN animal_age ag ON ag.id = a.animal_age_id " +
            "JOIN animal_castrated ac ON ac.id = a.animal_castrated_id " +
            "JOIN animal_color aco ON aco.id = a.animal_color_id " +
            "JOIN animal_gender age ON age.id = a.animal_gender_id " +
            "JOIN animal_size asi ON asi.id = a.animal_size_id " +
            "WHERE a.animal_type_id = :#{#user.animalType.id} " +
            "AND a.status = :#{#status.name()} " +
            "AND a.user_id != :#{#user.id} ",
            nativeQuery = true)
    Long countSuggestedByUser(@Param("user") User user, @Param("status") AnnounceStatus status);
}
