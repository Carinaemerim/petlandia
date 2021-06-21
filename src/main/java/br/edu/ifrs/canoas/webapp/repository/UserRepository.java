package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportType;
import br.edu.ifrs.canoas.webapp.enums.UserStatus;
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
    Optional<User> findByUsernameAndStatus(String username, UserStatus status);
    List<User> findAll();
    Page<User> findAllByOrderByNameDescIdDesc(Pageable page);
    Page<User> findAllByNameContainsIgnoreCaseOrCpfContainingOrUsernameIgnoreCaseContainingOrEmailContainingIgnoreCaseOrderByNameDescIdDesc(Pageable page, String name, String cpf, String username, String email);
    Long countAllByStatus(UserStatus status);
    @Query(value = "" +
            "select count(*) " +
            "from user u " +
            "inner join report r on r.report_by_id = u.id " +
            "where u.id = :#{#user.id} and r.status = :#{#status.name()} " +
            "and r.type = :#{#type.name()} " +
            "", nativeQuery = true)
    Long countReportedByTypeAndStatus(@Param("user") User user, @Param("type") ReportType type, @Param("status") ReportStatus status);
    Optional<User> findByIdAndStatus(Long id, UserStatus status);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}