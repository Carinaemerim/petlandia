package br.edu.ifrs.canoas.webapp.service;


import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.repository.AnimalTypeRepository;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AnnounceServiceTest {

    @Autowired
    AnnounceService service;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private AnnounceRepository announceRepository;
    @Autowired
    private AnnounceService announceService;

    private static final String CITY_NAME = "my_city";

    @Test
    public void testPaginationNegative() {
        //given
        Announce announce = new Announce();
        announce.setDescription("Um dog que come muita comida");
        announce.setName("anuncio");
        //announce.setCity(city);
        announceRepository.save(announce);

        //when
        Page<Announce> announces = announceService.findAll(-1, 100L, 200L);

        //then
        assertThat(announces.getPageable().getPageNumber()).isEqualTo(0);

    }

    @Test
    public void testFindAllCitiesNull() {
        //given

        //when
        Page<Announce> announces = announceService.findAll(0, null, 200L);

        //then
        assertThat(announces).isNotNull();

    }

    @Test
    public void testFindAllAnimalTypesNull() {

        //when
        Page<Announce> announces = announceService.findAll(0, 100L, null);

        //then
        assertThat(announces).isNotNull();

    }

    @Test
    public void testGetCityTypesNotNull() {

        //when
        //List<City> cities = announceService.getCityTypes();

        //then
        //assertThat(cities).isNotNull();
        //assertThat(cities).isNotEmpty();

    }

    @Test
    public void testGetAnimalTypesNotNull() {
        //given

        //when
        //List<AnimalType> animalTypes = announceService.getAnimalTypes();

        //then
        //assertThat(animalTypes).isNotNull();
        //assertThat(animalTypes).isNotEmpty();

    }

    @Test
    public void testFindById() {

        //when
        Announce announce = announceService.findById(66L);
        //then
        assertThat(announce.getId()).isEqualTo(66L);
    }

    public void testFindByIdNotExisting() {

        Assertions.assertThrows(EntityNotFoundException.class,

                () -> {
                    Announce announce = announceService.findById(555L);
                });

    }
}
