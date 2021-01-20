package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(AnnounceController.class)
public class AnnounceControllerTest extends BaseTest {
    @MockBean
    ReportService reportService;
    @MockBean
    AnimalCastratedService animalCastratedService;
    @MockBean
    AnimalGenderService animalGenderService;
    @MockBean
    AnimalSizeService animalSizeService;
    @MockBean
    AnimalTypeService animalTypeService;
    @MockBean
    AnimalAgeService animalAgeService;
    @MockBean
    AnimalColorService animalColorService;

    @Test
    public void testCreateGet() {
        fail("Not implemented");
    }
}
