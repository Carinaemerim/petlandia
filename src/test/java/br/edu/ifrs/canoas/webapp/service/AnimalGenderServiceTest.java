package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalGenderServiceTest extends BaseTest {
    @Autowired
    AnimalGenderService animalGenderService;

    public static final String GENDER_TYPE = "MALE";

    @Test
    public void testListAnimalAge() {
        List<AnimalGender> animalGenders = animalGenderService.listAnimalGender();

        assertThat(animalGenders).hasSize(2);
        assertThat(
                animalGenders.stream().anyMatch((e) -> e.getName().equals(GENDER_TYPE))
        ).isTrue();
    }
}
