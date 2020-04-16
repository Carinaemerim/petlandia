package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.webapp.domain.City;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(HomeController.class)
public class HomeControllerTest extends BaseControllerTest{

    @MockBean
    AnnounceService announceService;
    @MockBean
    UserDetailsImplService userDetailsImplService;

    @Test
    public void testGreetings() throws Exception{
        //given(announceService.getAnimalTypes()).willReturn(new ArrayList<AnimalType>());
        //given(announceService.getCityTypes()).willReturn(new ArrayList<City>());
        this.mvc.perform(get("/")
                .with(user(userDetails))
                .accept(MediaType.TEXT_HTML)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/index"))
                .andExpect(model().attribute("animalTypes"
                        ,allOf(notNullValue())
                )).andExpect(model().attribute("cities",
                allOf(notNullValue())))
        ;


    }



    private City createCity(){
        City city = new City();
        city.setDescription("canoas");
        return city;
    }

    private AnimalType createAnimalType(){
        AnimalType animalType = new AnimalType();
        //animalType.setName("cachorro fêmea");
        return animalType;
    }

}
