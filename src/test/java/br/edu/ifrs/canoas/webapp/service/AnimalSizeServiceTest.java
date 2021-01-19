package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalSizeServiceTest extends BaseTest {
    @Autowired
    AnimalSizeService animalSizeService;

    public static final String SIZE_TYPE = "MEDIUM";

    @Test
    public void testListAnimalAge() {
        List<AnimalSize> animalSizes = animalSizeService.listAnimalSize();

        assertThat(animalSizes).hasSize(3);
        assertThat(
                animalSizes.stream().anyMatch((e) -> e.getName().equals(SIZE_TYPE))
        ).isTrue();
    }
}
