package br.edu.ifrs.canoas.webapp.web.test.comment;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceListGeneralPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceViewPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.CommentSection;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

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

    @Test
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

    @Test
    public void testCreateCommentInvalidContent(){

    }

    @Test
    public void testCreateCommentBlank(){

    }

    @Test
    public void testCreateCommentUserNotLogged(){

    }

    @Test
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

    @Test
    public void testDeleteAnotherUSerComment(){

    }

    @Test
    public void testModeratorCanNotDeleteAnotherUserComment(){

    }

    @Test
    public void testAdminCanDeleteAnotherUserComment(){

    }
}
