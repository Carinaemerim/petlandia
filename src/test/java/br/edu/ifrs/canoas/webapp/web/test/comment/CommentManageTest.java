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

public class CommentManageTest extends BaseFluentTest {

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
    public void testCreateComment(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("r2d2", "user");

        homePage.isAt();
        homePage.getButtonHeaderAnnounces().click();

        announceListGeneralPage.isAt();
        announceListGeneralPage.clickItem(0);

        announceViewPage.isAt("150");
        announceViewPage.getCommentSubmitBtn().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);
        announceViewPage.getCommentContentInput().fill().withText("Que lindaaaaaaaaaa");
        announceViewPage.getCommentSubmitBtn().click();

        announceViewPage.isAt("150");
        announceViewPage.verifyAlertPresent(".alert-success");
        announceViewPage.getCommentSubmitBtn().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);

        assertThat(commentSection.hasCommentWithText("Que lindaaaaaaaaaa")).isTrue();
    }

    @Disabled
    public void testCreateCommentInvalidContent(){

    }

    @Disabled
    public void testCreateCommentBlank(){

    }

    @Disabled
    public void testCreateCommentUserNotLogged(){

    }

    @RetryingTest(5)
    public void testDeleteComment(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("admin", "user");

        homePage.isAt();

        announceViewPage.go("103");
        announceViewPage.getCommentSubmitBtn().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);
        assertThat(commentSection.hasCommentWithText(
                "Boa noite gostaria de mais informações."
        )).isTrue();
        commentSection.removeComment(1);

        announceViewPage.isAt("103");
        announceViewPage.verifyAlertPresent(".alert-success");
        announceViewPage.getCommentSubmitBtn().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);

        assertThat(commentSection.hasCommentWithText(
                "Boa noite gostaria de mais informações."
        )).isFalse();
    }

    @Disabled
    public void testDeleteAnotherUSerComment(){

    }

    @Disabled
    public void testModeratorCanNotDeleteAnotherUserComment(){

    }

    @Disabled
    public void testAdminCanDeleteAnotherUserComment(){

    }
}
