package br.edu.ifrs.canoas.webapp.web.test;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class LoginTest extends BaseFluentTest {
    @Page
    LoginPage loginPage;
    @Page
    HomePage homePage;

    @RetryingTest(5)
    public void testSuccessfullyLogin(){ ;
        homePage.go();
        loggedUser();
        homePage.isAt();
        assertThat(homePage.getTextHeaderUserName().text()).isEqualTo("R2d2");
    }

    @RetryingTest(5)
    public void testFailedLogin(){
        homePage.go();
        loginPage.go();
        loginPage.fillAndSubmitForm("r2d2", "wrongpassword");
        assertThat($(".alert.alert-danger").size()).isEqualTo(1);
    }
}