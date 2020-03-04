package br.edu.ifrs.canoas.lds.webapp.web.test;

import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifrs.canoas.lds.webapp.web.config.MyFluentTest;
import br.edu.ifrs.canoas.lds.webapp.web.page.ProfilePage;


public class ProfileTest extends MyFluentTest {

    @Page
    ProfilePage profilePage;

    @BeforeEach
    public void loginUser(){
        super.loginUser();
    }

    @Test
    public void checkIsAt() {
        //Given
    	profilePage.go(port);
    	profilePage.isAt();


        //When
        //and
        //and

        //Then
    }

}