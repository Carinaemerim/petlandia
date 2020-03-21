package br.edu.ifrs.canoas.webapp.web.config;

import static org.assertj.core.api.Assertions.assertThat;

import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.bonigarcia.wdm.WebDriverManager;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Wait
@FluentConfiguration(webDriver="chrome")
public abstract class MyFluentTest extends FluentTest {

    @LocalServerPort
    protected int port;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Page
    LoginPage loginPage;

    public void loginUser() {
        loginPage.go(port);
        loginPage.fillAndSubmitForm("user", "user")
                .awaitUntilFormDisappear();
        assertThat(window().title()).isEqualTo("IFRS");
    }

}