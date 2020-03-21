package br.edu.ifrs.canoas.webapp.repository;

import br.edu.ifrs.canoas.webapp.domain.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testSaveCity() {
        //given
        City city = new City();
        city.setDescription("Canoas");

        //when
        city = cityRepository.save(city);
        //then
        assertThat(city.getId()).isNotNull();
        City city1 = cityRepository.findById(city.getId()).orElse(null);
        assertThat(city).isNotNull();
        assertThat(city).isSameAs(city);

    }

    @Test
    public void testDeleteCity() {
        //given
        City city = new City();
        city.setDescription("Canoas");
        city = cityRepository.save(city);

        //when
        cityRepository.delete(city);
        //then
        assertThat(cityRepository.findById(city.getId()).orElse(null)).isNull();

    }

}
