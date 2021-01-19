package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.AnimalCastrated;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalCastratedServiceTest extends BaseTest {
    @Autowired
    AnimalCastratedService animalCastratedService;

    public static final String CASTRATED_TYPE = "YES";

    @Test
    public void testListAnimalCastrated() {
        List<AnimalCastrated> list = animalCastratedService.listAnimalCastrated();

        assertThat(list).hasSize(3);
        assertThat(
                list.stream().anyMatch((e) -> e.getName().equals(CASTRATED_TYPE))
        ).isTrue();
    }
}
