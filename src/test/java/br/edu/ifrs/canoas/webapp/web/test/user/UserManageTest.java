package br.edu.ifrs.canoas.webapp.web.test.user;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerIndexPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerProfileEditPage;
import br.edu.ifrs.canoas.webapp.web.page.manager.ManagerProfilePage;
import br.edu.ifrs.canoas.webapp.web.page.user.UserCreatePage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class UserManageTest extends BaseFluentTest {

    @Page()
    HomePage homePage;

    @Page()
    GenericPage genericPage;

    @Page()
    UserCreatePage userCreatePage;

    @Page()
    LoginPage loginPage;

    @Page()
    ManagerIndexPage managerIndexPage;

    @Page()
    ManagerProfilePage managerProfilePage;

    @Page()
    ManagerProfileEditPage managerProfileEditPage;

    @RetryingTest(5)
    public void testCreateUser() {
        homePage.go();
        genericPage.getButtonHeaderUserCreate().click();

        userCreatePage.isAt();

        userCreatePage.fillForm(
            "john.doe",
                "212.281.780-11",
                "useras",
                "useras",
                "92032-360",
                "RS",
                "Canoas",
                "Estância Velha",
                "Rua Ernesto da Silva rocha",
                "555",
                "John Doe",
                "john.doe@gmail.com",
                "(44) 4444-44444",
                "(44) 4444-44444",
                userCreatePage.getAnimalTypeDogField(),
                userCreatePage.getAnimalGenderMale(),
                "Pequeno",
                "Marrom",
                userCreatePage.getAnimalCastratedYesField(),
                "Geriatra (Mais de 12 anos)"
        );

        this.userCreatePage.getSubmitButton().click();
        loginPage.isAt();

        loginPage.fillAndSubmitForm("john.doe", "useras");

        homePage.isAt();

        assertThat(genericPage.getTextHeaderUserName().present()).isTrue();
        assertThat(genericPage.getTextHeaderUserName().text()).isEqualTo("John Doe");
    }

    @Test
    public void testCreateUserSameCPF() {

    }

    @Test
    public void testCreateUserSameUsername() {

    }

    @Test
    public void testCreateUserRequiredFieldBlank() {

    }

    @Test
    public void testCreateUserPasswordsDoesNotMatch() {

    }

    @RetryingTest(5)
    public void testEditUser() {
        this.loginPage.go();
        this.loginPage.fillAndSubmitFormAwait(
            "r2d2",
            "user"
        );

        this.homePage.isAt();

        this.genericPage.getButtonHeaderManager().click();

        this.managerIndexPage.isAt();
        this.managerIndexPage.getSidebarUserMyDataLink().click();

        this.managerProfilePage.isAt();
        this.managerProfilePage.getBtnEditUser().click();

        this.managerProfileEditPage.isAt();
        this.managerProfileEditPage.getStateFormField().fill().withText("MG");
        this.managerProfileEditPage.getBtnSubmit().scrollIntoView();

        await().explicitlyFor(4, TimeUnit.SECONDS);
        this.managerProfileEditPage.getBtnSubmit().click();

        this.managerProfilePage.isAt();
        this.managerProfilePage.verifyAlertPresent(".alert-success");

        assertThat(this.managerProfilePage.getStateFormField().text()).isEqualTo("MG");
    }

    @Test
    public void testEditUserPassword() {

    }

    @Test
    public void testEditUserWrongActualPassword() {

    }

    @Test
    public void testEditUserPasswordsDoesNotMatch() {

    }

    @Test
    public void testEditUserSameCPF() {

    }

    @Test
    public void testEditUserSameUsername() {

    }
}
