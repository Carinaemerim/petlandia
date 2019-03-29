package br.edu.ifrs.canoas.lds.webapp.repository;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AnimalTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @Test
    public void testSaveAnimalType(){
        //given
        AnimalType animalType = new AnimalType();
        animalType.setName("cachorro");

        //when
        animalType = animalTypeRepository.save(animalType);
        //then
        assertThat(animalType.getId()).isNotNull();
        AnimalType animalType1 = animalTypeRepository.findById(animalType.getId()).orElse(null);
        assertThat(animalType1).isNotNull();
        assertThat(animalType1).isSameAs(animalType);

    }

    @Test
    public void testDeleteAnimalType(){
        //given
        AnimalType animalType = new AnimalType();
        animalType.setName("cachorro");
        animalType = animalTypeRepository.save(animalType);

        //when
        animalTypeRepository.delete(animalType);
        //then
        assertThat(animalTypeRepository.findById(animalType.getId()).orElse(null)).isNull();

    }

}
