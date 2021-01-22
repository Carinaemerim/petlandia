package br.edu.ifrs.canoas.webapp.web.test;

import br.edu.ifrs.canoas.webapp.web.config.MyFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.ProfilePage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProfileTest extends MyFluentTest {

    @Page
    ProfilePage profilePage;

    @BeforeEach
    public void loginUser() {
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