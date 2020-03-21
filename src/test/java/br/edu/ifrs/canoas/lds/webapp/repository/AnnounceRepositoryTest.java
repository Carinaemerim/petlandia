package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import br.edu.ifrs.canoas.lds.webapp.domain.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AnnounceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AnnounceRepository announceRepository;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private CityRepository cityRepository;

    private static final String CITY_NAME = "my_city";
    private static final String ANIMAL_NAME = "my_animal";

    @Test
    public void testSaveAnnounce(){
        //given
        Announce announce = new Announce();
        announce.setDescription("Dogão dos bons.");

        //when
        announce = announceRepository.save(announce);
        //then
        assertThat(announce.getId()).isNotNull();
        Announce announce1 = announceRepository.findById(announce.getId()).orElse(null);
        assertThat(announce).isNotNull();
        assertThat(announce1).isSameAs(announce);

    }

    @Test
    public void testDeleteAnnounce(){
        //given
        Announce announce = new Announce();
        announce.setDescription("Um dog que come muita comida");
        announce = announceRepository.save(announce);

        //when
        announceRepository.delete(announce);
        //then
        assertThat(announceRepository.findById(announce.getId()).orElse(null)).isNull();

    }

    @Test
    public void testFilterByCity(){
        //given
        Announce announce = new Announce();
        City city = cityRepository.save(createCity());
        announce.setDescription("Um dog que come muita comida");
        announce.setName("anuncio");
        //announce.setCity(city);
        announce = announceRepository.save(announce);

        ExampleMatcher example = ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withMatcher("city", exact())
                .withIgnorePaths("id")
                .withIgnorePaths("description")
                .withIgnorePaths("name");

        Pageable page = PageRequest.of(0, 10, Sort.by("date").descending());
        //when
        Page<Announce> announces = announceRepository.findAll(Example.of(announce, example), page);
        //then

        assertThat(announces.getNumberOfElements()).isEqualTo(1);
        //assertThat(announces.getContent().get(0).getCity().getDescription()).isEqualTo(CITY_NAME);
    }

    @Test
    public void testFilterByAnimalType(){
        //given
        Announce announce = new Announce();
        AnimalType animalType = animalTypeRepository.save(createAnimalType());
        announce.setDescription("Um dog que come muita comida");
        announce.setName("anuncio");
        announce.setAnimalType(animalType);
        announce = announceRepository.save(announce);

        ExampleMatcher example = ExampleMatcher.matchingAll().withIgnoreNullValues()
                .withMatcher("animalType", exact())
                .withIgnorePaths("id")
                .withIgnorePaths("description")
                .withIgnorePaths("name");

        Pageable page = PageRequest.of(0, 10, Sort.by("date").descending());
        //when
        Page<Announce> announces = announceRepository.findAll(Example.of(announce, example), page);
        //then

        assertThat(announces.getNumberOfElements()).isEqualTo(1);
        assertThat(announces.getContent().get(0).getAnimalType().getName()).isEqualTo(ANIMAL_NAME);

    }

    private City createCity(){
        City city = new City();
        city.setDescription(CITY_NAME);
        return city;
    }

    private AnimalType createAnimalType(){
        AnimalType animalType= new AnimalType();
        animalType.setName(ANIMAL_NAME);
        return animalType;

    }

}
