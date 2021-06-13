package br.edu.ifrs.canoas.webapp.web.config;

import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Wait
@FluentConfiguration(webDriver = "chrome")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public abstract class BaseFluentTest extends FluentTest {

    @LocalServerPort
    protected int port;

    @Page
    HomePage homePage;
    @Page
    LoginPage loginPage;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Override
    public String getBaseUrl() {
        return String.format("http://localhost:%d", port);
    }

    public void notloggedUser() {
        homePage.go();
        assertThat(window().title()).isEqualTo("Home - PetLandia");
    }

    public void loggedUser() {
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("r2d2", "user");
    }

    public void loggedModerator() {
        loginPage.go();
        loginPage.fillAndSubmitFormAwait("mod", "user");
    }

    public void loggedAdmin() {
        loginPage.go(port);
        loginPage.fillAndSubmitFormAwait("admin", "user");
    }

}