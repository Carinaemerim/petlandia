package br.edu.ifrs.canoas.webapp.service;


import br.edu.ifrs.canoas.webapp.dao.AnnounceDao;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AnnounceServiceTest extends BaseTest {

    @Autowired
    AnnounceService announceService;

    @Autowired
    AnnounceRepository announceRepository;

    @Autowired
    UserRepository userRepository;

    @MockBean
    AnnounceDao announceDao;

    @Test
    public void testFindById() {
        Announce announce = announceService.findById(101L);

        assertThat(announce.getId()).isEqualTo(101L);
        assertThat(announce.getName()).isEqualTo("Rodolfo");
    }

    @Test
    public void testFindByIdNotFound() {
        assertThatExceptionOfType(AnnounceNotFoundException.class)
                .isThrownBy(() -> {
                    announceService.findById(10000L);
                });
    }

    @Test
    public void testFindByIdAndStatusActive() {
        Announce announce = announceService.findById(101L);

        assertThat(announce.getId()).isEqualTo(101L);
        assertThat(announce.getName()).isEqualTo("Rodolfo");
    }

    @Test
    public void testFindByIdAndStatusActiveNotFound() {
        assertThatExceptionOfType(AnnounceNotFoundException.class)
                .isThrownBy(() -> {
                    announceService.findById(10000L);
                });
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveNew() {
        Announce announce = AnnounceHelper.createAnnounce();
        announce.getAnimalAge().setId(101L);
        announce.getAnimalColor().setId(101L);
        announce.getAnimalCastrated().setId(101L);
        announce.getAnimalSize().setId(101L);
        announce.getAnimalType().setId(101L);
        announce.getAnimalGender().setId(101L);
        announce.getUser().setId(101L);

        announce = announceService.save(announce);

        assertThat(announce.getId()).isNotNull();

        assertThat(
                announceRepository.findById(announce.getId()).get()
        ).isEqualTo(announce);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveExisting() {
        Long id = 101L;
        Announce announce = announceRepository.findById(id).get();

        announce.setName("new name");

        announce = announceService.save(announce);

        assertThat(announce.getId()).isEqualTo(id);

        announce = announceRepository.findById(id).get();

        assertThat(announce.getName()).isEqualTo("new name");
    }

    @Test
    //TODO verificar paginação começa em zero
    public void testFindAllByUser() {
        PaginatedEntity<Announce> announces;
        User user = userRepository.findById(103L).get();

        announces = announceService.findAll(1, user, AnnounceStatus.ACTIVE);

        assertThat(announces.getCurrentPage()).isEqualTo(1);
        assertThat(announces.getPageLength()).isEqualTo(8);
        assertThat(announces.getTotalPages()).isEqualTo(2);
        assertThat(announces.getTotalResults()).isEqualTo(20);

        assertThat(announces.getData()).hasSize(8);
        assertThat(announces.getData().stream().allMatch(
                (e) -> e.getUser().equals(user)
        )).isTrue();
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void testFindFirstFive() {
    }

    @Test
    public void testCountAllByUser() {
    }

    @Test
    public void testCountAll() {
    }

    @Test
    public void testSetStatus() {
    }

    @Test
    public void testFindAllByFilter() {
    }

    @Test
    public void testRemoveAnnounce() {
    }

    @Test
    public void testRemoveAnnounceNotAllowed() {
    }
}
