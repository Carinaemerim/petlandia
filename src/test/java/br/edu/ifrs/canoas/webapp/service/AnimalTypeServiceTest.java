package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalTypeServiceTest extends BaseTest {
    @Autowired
    AnimalTypeService animalTypeService;

    public static final String TYPE = "DOG";

    @Test
    public void testListAnimalType() {
        List<AnimalType> animalTypes = animalTypeService.listAnimalType();

        assertThat(animalTypes).hasSize(2);
        assertThat(
                animalTypes.stream().anyMatch((e) -> e.getName().equals(TYPE))
        ).isTrue();
    }
}
