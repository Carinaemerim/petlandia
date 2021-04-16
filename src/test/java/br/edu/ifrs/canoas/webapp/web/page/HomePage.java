package br.edu.ifrs.canoas.webapp.web.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@PageUrl("http://localhost:{port}/")
public class HomePage extends FluentPage {

    @FindBy(id = "#user-login")
    private FluentWebElement loginButton;
    @FindBy(linkText = "Faça login")
    private FluentWebElement loginInfo;

    public void isAt() {
        assertThat(window().title()).isEqualTo("Home - PetLandia");
    }

}