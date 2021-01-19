package br.edu.ifrs.canoas.webapp.dao;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnounceDaoTest extends BaseTest {
    @Autowired
    AnnounceDao announceDao;

    @Test
    public void testFindAllByFilter() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(8);
        assertThat(announces.get(0).getId()).isEqualTo(150);
    }

    @Test
    public void testFindAllByFilterAnimalCastrated() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;
        List<Long> castratedFilters = new ArrayList<>();
        castratedFilters.add(101L);

        filters.setAnimalCastrated(castratedFilters);

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(8);
        assertThat(announces.get(0).getId()).isEqualTo(149);

        assertThat(announces.stream()
                .allMatch((e) -> castratedFilters.contains(e.getAnimalCastrated().getId()))
        ).isTrue();
    }

    @Test
    public void testFindAllByFilterAnimalColor() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;
        List<Long> colorFilters = new ArrayList<>();
        colorFilters.add(102L);

        filters.setAnimalColor(colorFilters);

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(8);
        assertThat(announces.get(0).getId()).isEqualTo(149);

        assertThat(announces.stream()
                .allMatch((e) -> colorFilters.contains(e.getAnimalColor().getId()))
        ).isTrue();
    }

    @Test
    public void testFindAllByFilterAnimalGender() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;
        List<Long> genderFilters = new ArrayList<>();
        genderFilters.add(101L);

        filters.setAnimalGender(genderFilters);

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(8);
        assertThat(announces.get(0).getId()).isEqualTo(149);

        assertThat(announces.stream()
                .allMatch((e) -> genderFilters.contains(e.getAnimalGender().getId()))
        ).isTrue();
    }

    @Test
    public void testFindAllByFilterAnimalSize() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;
        List<Long> sizeFilters = new ArrayList<>();
        sizeFilters.add(101L);

        filters.setAnimalSize(sizeFilters);

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(7);
        assertThat(announces.get(0).getId()).isEqualTo(149);

        assertThat(announces.stream()
                .allMatch((e) -> sizeFilters.contains(e.getAnimalSize().getId()))
        ).isTrue();
    }

    @Test
    public void testFindAllByFilterAnimalType() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;
        List<Long> typeFilters = new ArrayList<>();
        typeFilters.add(101L);

        filters.setAnimalType(typeFilters);

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(8);
        assertThat(announces.get(0).getId()).isEqualTo(150);

        assertThat(announces.stream()
                .allMatch((e) -> typeFilters.contains(e.getAnimalType().getId()))
        ).isTrue();
    }

    @Test
    public void testFindAllByFilterAnimalAge() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Announce> announces;
        List<Long> ageFilters = new ArrayList<>();
        ageFilters.add(101L);

        filters.setAnimalAge(ageFilters);

        announces = announceDao.findAllByFilter(filters, AnnounceStatus.ACTIVE, 8);

        assertThat(announces).hasSize(8);
        assertThat(announces.get(0).getId()).isEqualTo(143);

        assertThat(announces.stream()
                .allMatch((e) -> ageFilters.contains(e.getAnimalAge().getId()))
        ).isTrue();
    }

    @Test
    public void testCountAllByFilter() {
        AnnounceFilterForm filters = new AnnounceFilterForm();

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(49);
    }

    @Test
    public void testCountAllByFilterAnimalCastrated() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Long> castratedFilters = new ArrayList<>();
        castratedFilters.add(101L);

        filters.setAnimalCastrated(castratedFilters);

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(24);
    }

    @Test
    public void testCountAllByFilterAnimalColor() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Long> colorFilters = new ArrayList<>();
        colorFilters.add(102L);

        filters.setAnimalColor(colorFilters);

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(14);
    }

    @Test
    public void testCountAllByFilterAnimalGender() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Long> genderFilters = new ArrayList<>();
        genderFilters.add(101L);

        filters.setAnimalGender(genderFilters);

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(24);
    }

    @Test
    public void testCountAllByFilterAnimalSize() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Long> sizeFilters = new ArrayList<>();
        sizeFilters.add(101L);

        filters.setAnimalSize(sizeFilters);

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(7);
    }

    @Test
    public void testCountAllByFilterAnimalType() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Long> typeFilters = new ArrayList<>();
        typeFilters.add(101L);

        filters.setAnimalType(typeFilters);

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(33);
    }

    @Test
    public void testCountAllByFilterAnimalAge() {
        AnnounceFilterForm filters = new AnnounceFilterForm();
        List<Long> ageFilters = new ArrayList<>();
        ageFilters.add(101L);

        filters.setAnimalAge(ageFilters);

        Long counter = announceDao.countAllByFilter(filters, AnnounceStatus.ACTIVE);

        assertThat(counter).isEqualTo(14);
    }
}
