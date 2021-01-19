package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalAge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalAgeServiceTest extends BaseTest {
    @Autowired
    AnimalAgeService animalAgeService;

    public static final String AGE_TYPE = "ADULT";

    @Test
    public void testListAnimalAge() {
        List<AnimalAge> animalAges = animalAgeService.listAnimalAge();

        assertThat(animalAges).hasSize(5);
        assertThat(
                animalAges.stream().anyMatch((e) -> e.getName().equals(AGE_TYPE))
        ).isTrue();
    }
}
