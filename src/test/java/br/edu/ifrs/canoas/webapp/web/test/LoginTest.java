package br.edu.ifrs.canoas.webapp.web.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import br.edu.ifrs.canoas.webapp.web.config.MyFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;


public class LoginTest extends MyFluentTest {

    @Page
    LoginPage loginPage;

    @Test
    public void checkLoginSucceed() {
        //Given
        loginPage.go(port);
        //When
        loginPage.fillAndSubmitForm("user", "user")
                .awaitUntilFormDisappear();
        //Then
        assertThat(window().title()).isEqualTo("IFRS");
    }

    @Test
    public void checkLoginFailed() {
        //Given
        loginPage.go(port);
        //When
        loginPage.fillAndSubmitForm("wrongUser", "wrongPass");
        //Then
        assertThat($(".alert")).hasSize(1);
        loginPage.isAt();
    }


}