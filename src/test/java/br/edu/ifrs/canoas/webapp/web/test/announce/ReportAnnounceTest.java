package br.edu.ifrs.canoas.webapp.web.test.announce;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceListGeneralPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceViewPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportAnnounceTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    AnnounceViewPage announceViewPage;

    @Page
    HomePage homePage;

    @Page
    AnnounceListGeneralPage announceListGeneralPage;

    @Test
    public void testReportAnnounce(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("r2d2", "user");
        homePage.isAt();
        homePage.getButtonHeaderAnnounces().click();

        announceListGeneralPage.isAt();
        announceListGeneralPage.clickItem(0);

        announceViewPage.go("150");
        announceViewPage.reportAnnounce("Esse cachorro é meu!!!!!");

        announceViewPage.go("150");
        assertThat(announceViewPage.getAnnounceTitle().present()).isFalse();
    }

    @Test
    public void testBlankMotiveReportAnnounce(){

    }

    @Test
    public void testReportAnnounceUserNotLogged(){

    }

    @Test
    public void testUserCanNotReportHisOwnAnnounce(){

    }
}
