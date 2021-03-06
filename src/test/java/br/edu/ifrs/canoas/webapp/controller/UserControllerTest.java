package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.service.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(UserController.class)
public class UserControllerTest extends BaseTest {
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

    @Disabled
    @Test
    public void testCreateUserGet() {
        fail("Not implemented");
    }
}