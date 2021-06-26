package br.edu.ifrs.canoas.webapp.web.test.announce;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerAnnouncesPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewAnnounceTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    HomePage homePage;

    @Page
    ManagerIndexPage managerIndexPage;

    @Page
    ManagerAnnouncesPage managerAnnouncesPage;

    @RetryingTest(5)
    public void testAcceptAnnounceReview(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("mod", "user");
        homePage.isAt();

        homePage.getButtonHeaderManager().click();
        managerIndexPage.isAt();
        managerIndexPage.getReportedAnnouncesLink().click();

        managerAnnouncesPage.isAt();
        managerAnnouncesPage.acceptReport(0);

        managerAnnouncesPage.isAt();
        managerAnnouncesPage.verifyAlertPresent(".alert-success");
        assertThat($(managerAnnouncesPage.getItemSelector()).present()).isFalse();
    }

    @Disabled
    public void testSeeReviewedAnnounceDetails(){

    }

    @RetryingTest(5)
    public void testRejectAnnounceReview(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("mod", "user");
        homePage.isAt();

        homePage.getButtonHeaderManager().click();
        managerIndexPage.isAt();
        managerIndexPage.getReportedAnnouncesLink().click();

        managerAnnouncesPage.isAt();
        managerAnnouncesPage.rejectReport(0);

        managerAnnouncesPage.isAt();
        managerAnnouncesPage.verifyAlertPresent(".alert-success");
        assertThat($(managerAnnouncesPage.getItemSelector()).present()).isFalse();
    }

    @Disabled
    public void testUserCanNotReviewAnnounce(){

    }
}
