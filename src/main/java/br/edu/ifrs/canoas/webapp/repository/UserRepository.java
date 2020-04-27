package br.edu.ifrs.canoas.webapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrs.canoas.webapp.domain.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);
    //List<User> findAllByUsernameContains(String username);
    List<User> findAll();

    Page<User> findAllByOrderByNameDescIdDesc(Pageable page);
    Page<User> findAllByNameContainsIgnoreCaseOrCpfContainingOrUsernameIgnoreCaseContainingOrEmailContainingIgnoreCaseOrderByNameDescIdDesc(Pageable page, String name, String cpf, String username, String email);
}