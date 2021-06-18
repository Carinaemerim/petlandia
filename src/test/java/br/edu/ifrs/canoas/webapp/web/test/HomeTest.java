package br.edu.ifrs.canoas.webapp.web.test;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.user.UserCreatePage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

public class HomeTest extends BaseFluentTest {
    @Page
    HomePage homePage;
    @Page
    LoginPage loginPage;
    @Page
    UserCreatePage userCreatePage;

    @Test
    public void testLoginSuggestedAnnounces(){
        homePage.go();
        homePage.getButtonHomeUserLogin().click();
        loginPage.isAt();
    }

    @Test
    public void testRegisterSuggestedAnnounces(){
        homePage.go();
        homePage.getButtonHomeUserCreate().click();
        userCreatePage.isAt();
    }
}