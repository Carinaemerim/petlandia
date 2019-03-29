package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testSaveCity(){
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
    public void testDeleteCity(){
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
