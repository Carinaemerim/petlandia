package br.edu.ifrs.canoas.webapp.web.config;

import br.edu.ifrs.canoas.webapp.web.config.browser.BrowserInterface;
import br.edu.ifrs.canoas.webapp.web.page.HomePage;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Wait
@FluentConfiguration(
        htmlDumpMode = ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL,
        htmlDumpPath = "test_html_dumps",
        screenshotMode = ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL,
        screenshotPath = "test_screenshots"
)

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public abstract class BaseFluentTest extends FluentTest {
    private static final Logger log = LoggerFactory.getLogger(BaseFluentTest.class);
    @LocalServerPort
    protected int port;

    @Page
    HomePage homePage;
    @Page
    LoginPage loginPage;

    @BeforeAll
    public static void setupClass() {
        getBrowser().manage();
    }

    @Override
    public WebDriver newWebDriver() {
        WebDriver webDriver = this.createWebDriver();
        webDriver.manage().window().setSize(new Dimension(1366, 768));
        return webDriver;
    }

    private WebDriver createWebDriver() {
        log.info(() -> "Running test locally using " + SeleniumBrowserConfigProperties.getBrowserName());
        return super.newWebDriver();
    }

    @Override
    public String getWebDriver() {
        return SeleniumBrowserConfigProperties.getBrowserName();
    }

    @Override
    public Capabilities getCapabilities() {
        return getBrowser().getCapabilities();
    }

    @Override
    public String getBaseUrl() {
        return SeleniumBrowserConfigProperties.getPageUrl(port);
    }

    private static BrowserInterface getBrowser() {
        BrowserInterface browser = BrowserInterface.getBrowser(
            SeleniumBrowserConfigProperties.getBrowserName()
        );
        browser.setHeadless(SeleniumBrowserConfigProperties.isHeadless());
        return browser;
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