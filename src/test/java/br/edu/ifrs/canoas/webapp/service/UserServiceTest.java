package br.edu.ifrs.canoas.webapp.service;


import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.helper.UserHelper;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserServiceTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSaveNew() {
        User user = UserHelper.createUser();
        String password = user.getPassword();

        user.getAnimalAge().setId(101L);
        user.getAnimalColor().setId(101L);
        user.getAnimalCastrated().setId(101L);
        user.getAnimalSize().setId(101L);
        user.getAnimalType().setId(101L);
        user.getAnimalGender().setId(101L);

        user = this.userService.save(user);

        assertThat(user.getId()).isNotNull();
        assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);
        // Deve fazer hash de senha para novos usuários
        assertThat(user.getPassword()).isNotEqualTo(password);
    }

    @Test
    public void testSaveExisting() {
        Long id = 101L;
        User user = userRepository.findById(101L).get();

        user.setName("Novo nome");

        userService.save(user);

        assertThat(user.getId()).isEqualTo(id);

        user = userRepository.findById(101L).get();

        assertThat(user.getName()).isEqualTo("Novo nome");
    }

    @Test
    public void testGetOne() {
        User user = userRepository.findById(101L).get();

        assertThat(userService.getOne(user)).isEqualTo(user);
    }

    @Test
    public void testGetOneNotFound() {
        User user = userRepository.findById(101L).get();

        user.setId(100000L);

        assertThat(userService.getOne(user)).isNull();
    }

    @Test
    public void testFindById() {
        Long id = 101L;

        User user = userService.findById(id);

        assertThat(user.getId()).isEqualTo(id);

        assertThat(userRepository.findById(id).get()).isEqualTo(user);
    }

    @Test
    public void testFindByIdNotFound() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> {
                    userService.findById(10000000L);
                });
    }

    @Test
    public void testFindAll() {
        PaginatedEntity<User> users;

        users = userService.findAll(0, 8, "");

        assertThat(users.getCurrentPage()).isEqualTo(0);
        assertThat(users.getPageLength()).isEqualTo(8);
        assertThat(users.getTotalPages()).isEqualTo(0);
        assertThat(users.getTotalResults()).isEqualTo(5);

        assertThat(users.getData()).hasSize(5);

        assertThat(users.getData().get(0).getId()).isEqualTo(100);
    }

    @Test
    public void testFindAllWithTerm() {
        PaginatedEntity<User> users;
        String term = "r2";

        users = userService.findAll(0, 8, term);

        assertThat(users.getCurrentPage()).isEqualTo(0);
        assertThat(users.getPageLength()).isEqualTo(8);
        assertThat(users.getTotalPages()).isEqualTo(0);
        assertThat(users.getTotalResults()).isEqualTo(1);

        assertThat(users.getData()).hasSize(1);

        assertThat(users.getData().get(0).getId()).isEqualTo(100);

        assertThat(users.getData().stream().allMatch(
                (e) -> {
                    Boolean inCpf = e.getCpf().toLowerCase().contains(term);
                    Boolean inUsername = e.getUsername().toLowerCase().contains(term);
                    Boolean inName = e.getName().toLowerCase().contains(term);
                    Boolean inEmail = e.getEmail().toLowerCase().contains(term);

                    return inName || inUsername || inCpf || inEmail;
                }
        )).isTrue();
    }
}