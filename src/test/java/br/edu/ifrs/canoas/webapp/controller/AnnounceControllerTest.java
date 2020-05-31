package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;
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
    @Test
    public void testReturnDetails() throws Exception{

        given(announceService.findById(any(Long.class))).willReturn(createAnnounce());

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

    private Announce createAnnounce(){
        Announce announce = new Announce();
        announce.setId(10L);
        announce.setName("Alice");
        announce.setAnimalType(createAnimalType());
        announce.setUser(super.user);
        return announce;
    }

    private AnimalType createAnimalType(){
        AnimalType animalType = new AnimalType();
        //animalType.setName("cachorro fêmea");
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
