package br.edu.ifrs.canoas.lds.webapp.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AnnounceServiceTest {

    @Autowired
    AnnounceService service;

    @Test
    public void testDeleteEditAnnounceUser(){
        //given

        //when

        //then

    }

    @Test
    public void testDeleteAndEditAnnounceAdmin(){
        //given

        //when

        //then

    }

}
