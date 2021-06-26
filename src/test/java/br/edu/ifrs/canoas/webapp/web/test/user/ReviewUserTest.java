package br.edu.ifrs.canoas.webapp.web.test.user;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerUsersPage;
import org.fluentlenium.core.annotation.Page;
import org.junitpioneer.jupiter.RetryingTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewUserTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    HomePage homePage;

    @Page
    ManagerIndexPage managerIndexPage;

    @Page
    ManagerUsersPage managerUsersPage;

    @RetryingTest(5)
    public void testAcceptUserReview() {
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("admin", "user");
        homePage.isAt();

        homePage.getButtonHeaderManager().click();
        managerIndexPage.isAt();
        managerIndexPage.getReportedUsersLink().click();

        managerUsersPage.isAt();
        managerUsersPage.acceptReport(0);

        managerUsersPage.isAt();
        managerUsersPage.verifyAlertPresent(".alert-success");
        assertThat($(managerUsersPage.getItemSelector()).present()).isFalse();
    }

    @RetryingTest(5)
    public void testRejectUserReview() {
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("admin", "user");
        homePage.isAt();

        homePage.getButtonHeaderManager().click();
        managerIndexPage.isAt();
        managerIndexPage.getReportedUsersLink().click();

        managerUsersPage.isAt();
        managerUsersPage.rejectReport(0);

        managerUsersPage.isAt();
        managerUsersPage.verifyAlertPresent(".alert-success");
        assertThat($(managerUsersPage.getItemSelector()).present()).isFalse();
    }
}
