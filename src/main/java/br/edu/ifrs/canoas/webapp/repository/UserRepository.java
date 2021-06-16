package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAll();

    Page<User> findAllByOrderByNameDescIdDesc(Pageable page);

    Page<User> findAllByNameContainsIgnoreCaseOrCpfContainingOrUsernameIgnoreCaseContainingOrEmailContainingIgnoreCaseOrderByNameDescIdDesc(Pageable page, String name, String cpf, String username, String email);

    @Query(value = "" +
            "select count(*) " +
            "from user u " +
            "inner join report r on r.report_by_id = u.id " +
            "where u.id = :#{#user.id} and r.status = :#{#status.name()} " +
            "and r.announce_id is not null and r.comment_id is not null and r.user_id is null " +
            "", nativeQuery = true)
    Long countReportedCommentsByStatus(@Param("user") User user, @Param("status") ReportStatus status);

    @Query(value = "" +
            "select count(*) " +
            "from user u " +
            "inner join report r on r.report_by_id = u.id " +
            "where u.id = :#{#user.id} and r.status = :#{#status.name()} " +
            "and r.announce_id is not null and r.comment_id is null and r.user_id is null " +
            "", nativeQuery = true)
    Long countReportedAnnouncesByStatus(@Param("user") User user, @Param("status") ReportStatus status);

    @Query(value = "" +
            "select count(*) " +
            "from user u " +
            "inner join report r on r.report_by_id = u.id " +
            "where u.id = :#{#user.id} and r.status = :#{#status.name()} " +
            "and r.announce_id is null and r.comment_id is null and r.user_id is not null " +
            "", nativeQuery = true)
    Long countReportedUsersByStatus(@Param("user") User user, @Param("status") ReportStatus status);
}