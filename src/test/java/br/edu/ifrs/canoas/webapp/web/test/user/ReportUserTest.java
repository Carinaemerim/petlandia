package br.edu.ifrs.canoas.webapp.web.test.user;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerAdminUsersPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerUserViewPage;
import org.fluentlenium.core.annotation.Page;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportUserTest extends BaseFluentTest {
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

    @RetryingTest(5)
    public void testReportUser() {
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("mod", "user");
        homePage.isAt();
        homePage.getButtonHeaderManager().click();

        managerIndexPage.isAt();
        managerIndexPage.getAdminUsersLink().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);
        managerIndexPage.getAdminUsersLink().click();

        managerAdminUsersPage.isAt();
        managerAdminUsersPage.viewUser(1);

        managerUserViewPage.isAt("100");
        managerUserViewPage.reportUser("Postou anúncio impróprio");

        managerUserViewPage.isAt("100");
        managerUserViewPage.logout();
        homePage.isAt();

        homePage.getButtonHomeUserLogin().click();
        loginPage.isAt();

        loginPage.fillAndSubmitForm("r2d2", "user");
        loginPage.isAt();
        loginPage.verifyAlertPresent(".alert-danger");
    }
}
