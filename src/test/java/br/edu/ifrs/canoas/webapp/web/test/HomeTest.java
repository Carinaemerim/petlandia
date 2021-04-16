package br.edu.ifrs.canoas.webapp.web.test;

import br.edu.ifrs.canoas.webapp.web.config.MyFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class HomeTest extends MyFluentTest {

    @Page
    HomePage homePage;

    @Test
    public void checkAccessNotLogged() {
        //Given
        homePage.go(port);
        //Then
        assertThat(window().title()).isEqualTo("Home - PetLandia");
    }

    @Test
    public void checkAccessLogged() {
        //given
        homePage.go(port);
        //when

        //Then
    }

    @Test
    public void testLoginSuggestedAnnounces(){

    }

    @Test
    public void testRegisterSuggestedAnnounces(){

    }


}