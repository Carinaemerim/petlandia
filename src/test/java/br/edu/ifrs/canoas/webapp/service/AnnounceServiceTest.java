package br.edu.ifrs.canoas.webapp.service;


import br.edu.ifrs.canoas.webapp.MockAuthContext;
import br.edu.ifrs.canoas.webapp.dao.AnnounceDao;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.ForbiddenException;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnnounceServiceTest extends BaseTest {

    @Autowired
    AnnounceService announceService;

    @Autowired
    AnnounceRepository announceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockAuthContext mockAuthContext;

    @MockBean
    AnnounceDao announceDao;

    @MockBean
    UserDetailsImplService userDetailsImplService;

    @AfterEach
    public void tearDown() {
        this.mockAuthContext.tearDown();
    }

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
    public void testFindAllByUser() {
        PaginatedEntity<Announce> announces;
        User user = userRepository.findById(103L).get();

        announces = announceService.findAll(0, user, AnnounceStatus.ACTIVE);

        assertThat(announces.getCurrentPage()).isEqualTo(0);
        assertThat(announces.getPageLength()).isEqualTo(8);
        assertThat(announces.getTotalPages()).isEqualTo(2);
        assertThat(announces.getTotalResults()).isEqualTo(20);

        assertThat(announces.getData()).hasSize(8);
        assertThat(announces.getData().stream().allMatch(
                (e) -> e.getUser().equals(user) && e.getStatus().equals(AnnounceStatus.ACTIVE)
        )).isTrue();

        assertThat(announces.getData().get(0).getId()).isEqualTo(150);
    }

    @Test
    public void testFindAll() {
        PaginatedEntity<Announce> announces;

        announces = announceService.findAll(0, AnnounceStatus.ACTIVE);

        assertThat(announces.getCurrentPage()).isEqualTo(0);
        assertThat(announces.getPageLength()).isEqualTo(8);
        assertThat(announces.getTotalPages()).isEqualTo(6);
        assertThat(announces.getTotalResults()).isEqualTo(49);

        assertThat(announces.getData()).hasSize(8);
        assertThat(announces.getData().get(0).getId()).isEqualTo(150);

        assertThat(announces.getData().stream().allMatch((e) -> e.getStatus().equals(AnnounceStatus.ACTIVE))).isTrue();
    }

    @Test
    public void testFindFirstFive() {
        List<Announce> announces = announceService.findFirstFive(AnnounceStatus.ACTIVE);

        assertThat(announces).hasSize(5);
        assertThat(announces.get(0).getId()).isEqualTo(150);

        assertThat(announces.stream().allMatch((e) -> e.getStatus().equals(AnnounceStatus.ACTIVE))).isTrue();
    }

    @Test
    public void testCountAllByUser() {
        User user = userRepository.findById(103L).get();

        Long counter = announceService.countAll(user, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(20);
    }

    @Test
    public void testCountAll() {
        Long counter = announceService.countAll(AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(49);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSetStatus() {
        Long id = 101L;

        Announce announce = announceRepository.findById(id).get();

        assertThat(announce.getStatus()).isEqualTo(AnnounceStatus.ACTIVE);

        announceService.setStatus(announce, AnnounceStatus.INACTIVE);

        announce = announceRepository.findById(id).get();

        assertThat(announce.getStatus()).isEqualTo(AnnounceStatus.INACTIVE);
    }

    @Test
    public void testFindAllByFilter() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        Announce announce = AnnounceHelper.createAnnounce();
        List<Announce> announceList = new ArrayList<>();

        announceList.add(announce);

        int pageLength = 10;

        filters.setPage(1);

        when(announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE)).thenReturn(10L);
        when(announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, pageLength))
                .thenReturn(announceList);

        PaginatedEntity<Announce> announces = announceService.findAll(
                filters,
                AnnounceStatus.ACTIVE,
                pageLength
        );

        verify(announceDao).countAllByFilter(filters, AnnounceStatus.ACTIVE);
        verify(announceDao).findAllByFilter(filters, AnnounceStatus.ACTIVE, pageLength);

        assertThat(announces.getCurrentPage()).isEqualTo(1);
        assertThat(announces.getTotalResults()).isEqualTo(10);
        assertThat(announces.getPageLength()).isEqualTo(pageLength);
        assertThat(announces.getTotalPages()).isEqualTo(0);

        assertThat(announces.getData().get(0)).isEqualTo(announce);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testRemoveAnnounce() {
        this.mockAuthContext.mockAuthAdmin();
        Long id = 101L;

        Announce announce = announceRepository.findById(id).get();
        assertThat(announce.getStatus()).isEqualTo(AnnounceStatus.ACTIVE);

        announceService.removeAnnounce(id);

        announce = announceRepository.findById(id).get();
        assertThat(announce.getStatus()).isEqualTo(AnnounceStatus.INACTIVE);

    }

    @Test
    public void testRemoveAnnounceNotAllowed() {
        this.mockAuthContext.mockAuthAnonymous();

        assertThatExceptionOfType(ForbiddenException.class)
                .isThrownBy(() -> {
                    announceService.removeAnnounce(101L);
                });
    }
}
