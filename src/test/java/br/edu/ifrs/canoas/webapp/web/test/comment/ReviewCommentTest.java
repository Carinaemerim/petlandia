package br.edu.ifrs.canoas.webapp.web.test.comment;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerCommentsPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewCommentTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    HomePage homePage;

    @Page
    ManagerIndexPage managerIndexPage;

    @Page
    ManagerCommentsPage managerCommentsPage;

    @RetryingTest(5)
    public void testAcceptCommentReview(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("mod", "user");
        homePage.isAt();

        homePage.getButtonHeaderManager().click();
        managerIndexPage.isAt();
        managerIndexPage.getReportedCommentsLink().click();

        managerCommentsPage.isAt();
        managerCommentsPage.acceptReport(0);

        managerCommentsPage.isAt();
        managerCommentsPage.verifyAlertPresent(".alert-success");
        assertThat($(managerCommentsPage.getItemSelector()).present()).isFalse();
    }

    @Test
    public void testSeeReviewedCommentDetails(){

    }

    @RetryingTest(5)
    public void testRejectCommentReview(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("mod", "user");
        homePage.isAt();

        homePage.getButtonHeaderManager().click();
        managerIndexPage.isAt();
        managerIndexPage.getReportedCommentsLink().click();

        managerCommentsPage.isAt();
        managerCommentsPage.rejectReport(0);

        managerCommentsPage.isAt();
        managerCommentsPage.verifyAlertPresent(".alert-success");
        assertThat($(managerCommentsPage.getItemSelector()).present()).isFalse();
    }

    @Test
    public void testUserCanNotReviewComment(){

    }
}
