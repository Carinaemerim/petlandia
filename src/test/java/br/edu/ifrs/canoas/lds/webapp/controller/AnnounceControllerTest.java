package br.edu.ifrs.canoas.lds.webapp.controller;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import br.edu.ifrs.canoas.lds.webapp.domain.City;
import br.edu.ifrs.canoas.lds.webapp.domain.File;
import br.edu.ifrs.canoas.lds.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.lds.webapp.service.UserDetailsImplService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(AnnounceController.class)
public class AnnounceControllerTest extends BaseControllerTest{

    @MockBean
    AnnounceService announceService;
    @MockBean
    UserDetailsImplService userDetailsImplService;
    @MockBean
    File photo;

    @Test
    public void testReturnDetails() throws Exception{

        given(announceService.findById(any(Long.class))).willReturn(createAnnounce());
        given(photo.getPictureBase64()).willReturn("photo");
        this.mvc.perform(get("/announce/details")
                .with(user(userDetails))
                .accept(MediaType.TEXT_HTML)
                .param("id", "120")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/announce/announceDetails"))
                .andExpect(model().attribute("announce"
                        ,allOf(
                                hasProperty("name", is("Alice")))
                ))
        ;

    }

    @Test
    public void testReturnFilter() throws Exception{

        given(announceService.findAll(any(int.class), any(Long.class), any(Long.class)))
        .willReturn(createPage());

        given(photo.getPictureBase64()).willReturn("photo");
        this.mvc.perform(get("/announce/filter")
                .with(user(userDetails))
                .accept(MediaType.TEXT_HTML)
                .param("page", "1")
                .param("cityId", "10")
                .param("animalTypeId", "100")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/announce/fragments/announce-list"))
                .andExpect(model().attribute("currentPage", is(1)))
        ;

    }

    @Test
    public void testReturnFilterInvalidPage() throws Exception{

        given(announceService.findAll(any(int.class), any(Long.class), any(Long.class)))
                .willReturn(createPage());

        given(photo.getPictureBase64()).willReturn("photo");
        this.mvc.perform(get("/announce/filter")
                .with(user(userDetails))
                .accept(MediaType.TEXT_HTML)
                .param("page", "-1")
                .param("cityId", "10")
                .param("animalTypeId", "100")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/announce/fragments/announce-list"))
                .andExpect(model().attribute("currentPage", is(1)))
        ;

    }

    private Announce createAnnounce(){
        Announce announce = new Announce();
        announce.setId(10L);
        announce.setName("Alice");
        announce.setCity(createCity());
        announce.setType(createAnimalType());
        announce.setUser(super.user);
        announce.setPhoto(photo);
        return announce;
    }

    private City createCity(){
        City city = new City();
        city.setDescription("canoas");
        return city;
    }

    private AnimalType createAnimalType(){
        AnimalType animalType = new AnimalType();
        animalType.setName("cachorro fêmea");
        return animalType;
    }

    private Page<Announce> createPage(){

        List<Announce> announceList = new ArrayList<>();
        announceList.add(createAnnounce());
        announceList.add(createAnnounce());

        Pageable pageable = PageRequest.of(1, 10);

        return new PageImpl<>(announceList, pageable, announceList.size());
    }

}
