package br.edu.ifrs.canoas.webapp.web.test.announce;

import br.edu.ifrs.canoas.webapp.helper.FileHelper;
import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceCreatePage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceEditPage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceListActivePage;
import br.edu.ifrs.canoas.webapp.web.page.announce.AnnounceViewPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnounceManageTest extends BaseFluentTest {

    @Page
    LoginPage loginPage;

    @Page
    HomePage homePage;

    @Page
    AnnounceCreatePage announceCreatePage;

    @Page
    AnnounceViewPage announceViewPage;

    @Page
    ManagerIndexPage managerIndexPage;

    @Page
    AnnounceEditPage announceEditPage;

    @Page
    AnnounceListActivePage announceListActivePage;

    @Test
    public void testCreateAnnounce() throws IOException {
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("r2d2", "user");

        homePage.isAt();
        homePage.getButtonHeaderAnnounceCreate().click();

        announceCreatePage.isAt();
        announceCreatePage.fillAnnounceFormTop(
            "Boxer MoDeus",
                "Thor"
        );

        Resource resource = FileHelper.loadPngFile();
        String path = resource.getFile().getAbsolutePath();

        announceCreatePage.fillMainCropper(path);
        announceCreatePage.fillSecondCropper(path);
        announceCreatePage.fillThirdCropper(path);

        announceCreatePage.fillAnimalForm(
            announceCreatePage.getAnimalTypeDogField(),
            announceCreatePage.getAnimalGenderMale(),
            "Pequeno",
            "Marrom",
            announceCreatePage.getAnimalCastratedYesField(),
            "Geriatra (Mais de 12 anos)"
        );

        announceCreatePage.fillAddressForm(
            "92032-360",
            "RS",
            "Canoas",
            "Estância Velha",
            "Rua Ernesto da Silva rocha",
            "555"
        );

        announceCreatePage.fillAnnounceFormBottom(
            "Um cachorro boxer, com cara de MoDeus, que pede muita comida"
        );

        announceCreatePage.getSubmitButton().click();

        announceViewPage.isAt(151);
        announceViewPage.verifyAlertPresent(".alert-success");

        assertThat(announceViewPage.getAnnounceTitle().text()).isEqualTo("Boxer MoDeus");

    }

    @Test
    public void testCreateAnnounceUserNotLoggedIn(){

    }

    @Test
    public void testCreateAnnounceRequiredFieldBlank(){

    }

    @Test
    public void testCreateAnnounceLessCharactersField(){

    }


    @Test
    public void testCreateAnnounceMoreCharactersField(){

    }

    @Test
    public void testEditAnnounce(){
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("r2d2", "user");

        homePage.isAt();
        homePage.getButtonHeaderManager().click();

        managerIndexPage.isAt();
        managerIndexPage.getActiveAnnouncesLink().click();

        announceListActivePage.isAt();
        announceListActivePage.clickItem(0);

        announceViewPage.isAt("126");
        announceViewPage.getEditAnnounceBtn().click();

        announceEditPage.isAt("126");
        announceEditPage.fillAnnounceFormTop(
                "Boxer MoDeus",
                "Thor"
        );
        announceEditPage.getSubmitButton().scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);
        announceEditPage.getSubmitButton().click();

        announceViewPage.isAt("126");
        announceViewPage.verifyAlertPresent(".alert-success");

        assertThat(announceViewPage.getAnnounceTitle().text()).isEqualTo("Boxer MoDeus");
    }

    @Test
    public void testEditAnnounceRequiredFieldBlank(){

    }

    @Test
    public void testEditAnnounceInvalidData(){

    }

    @Test
    public void testEditAnnounceLessCharactersField(){

    }


    @Test
    public void testEditAnnounceMoreCharactersField(){

    }


}
