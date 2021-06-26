package br.edu.ifrs.canoas.webapp.web.test.comment;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceListGeneralPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceViewPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.CommentSection;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportCommentTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    HomePage homePage;

    @Page
    AnnounceListGeneralPage announceListGeneralPage;

    @Page
    AnnounceViewPage announceViewPage;

    @Page
    CommentSection commentSection;

    @RetryingTest(5)
    public void testReportComment(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("r2d2", "user");

        homePage.isAt();

        announceViewPage.go("103");
        announceViewPage.getCommentSubmitBtn().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);
        assertThat(commentSection.hasCommentWithText(
                "Boa noite gostaria de mais informações."
        )).isTrue();
        commentSection.reportComment(1, "Comentário ofensivo");

        announceViewPage.isAt("103");
        announceViewPage.verifyAlertPresent(".alert-success");
        announceViewPage.getCommentSubmitBtn().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);

        assertThat(commentSection.hasCommentWithText(
                "Boa noite gostaria de mais informações."
        )).isFalse();
    }

    @Disabled
    public void testBlankMotiveReportComment(){

    }

    @Disabled
    public void testReportCommentUserNotLogged(){

    }

    @Disabled
    public void testUserCanNotReportHisOwnComment(){

    }
}
