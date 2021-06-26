package br.edu.ifrs.canoas.webapp.web.test.user;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerAdminUsersPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerUserViewPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.concurrent.TimeUnit;

public class BanUserTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    HomePage homePage;

    @Page
    ManagerIndexPage managerIndexPage;

    @Page
    ManagerAdminUsersPage managerAdminUsersPage;

    @Page
    ManagerUserViewPage managerUserViewPage;

    @Disabled
    public void testBanUser(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("admin", "user");
        homePage.isAt();
        homePage.getButtonHeaderManager().click();

        managerIndexPage.isAt();
        managerIndexPage.getAdminUsersLink().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);
        managerIndexPage.getAdminUsersLink().click();

        managerAdminUsersPage.isAt();
        managerAdminUsersPage.viewUser(1);

        managerUserViewPage.isAt("100");
        managerUserViewPage.blockUser();

        managerUserViewPage.isAt("100");
        managerUserViewPage.logout();
        homePage.isAt();

        homePage.getButtonHomeUserLogin().click();
        loginPage.isAt();

        loginPage.fillAndSubmitFormAwait("r2d2", "user");
        loginPage.isAt();
        loginPage.verifyAlertPresent(".alert-danger");
    }

    @Disabled
    public void testAdminCanNotBanHimself(){

    }

    @Disabled
    public void testCreateUserCPFBanned(){

    }

    @Disabled
    public void testCreateUserUsernameBanned(){

    }

    @Disabled
    public void testUserCanNotBanUser(){

    }

    @Disabled
    public void testModeratorCanNotBanUser(){

    }
}
