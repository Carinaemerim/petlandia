package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalColor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalColorServiceTest extends BaseTest {
    @Autowired
    AnimalColorService animalColorService;

    public static final String COLOR_TYPE = "CARAMEL";

    @Test
    public void testListAnimalColor() {
        List<AnimalColor> animalColors = animalColorService.listAnimalColor();

        assertThat(animalColors).hasSize(6);
        assertThat(
                animalColors.stream().anyMatch((e) -> e.getName().equals(COLOR_TYPE))
        ).isTrue();
    }
}
