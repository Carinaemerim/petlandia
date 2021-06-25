package br.edu.ifrs.canoas.webapp.web.page;

import lombok.Getter;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

@Getter
public class GenericPage extends FluentPage {
    @FindBy(id = "btn-header-home")
    private FluentWebElement buttonHeaderHome;

    @FindBy(id = "btn-header-announces")
    private FluentWebElement buttonHeaderAnnounces;

    @FindBy(id = "btn-header-suggested")
    private FluentWebElement buttonHeaderSuggested;

    @FindBy(id = "btn-header-announce-create")
    private FluentWebElement buttonHeaderAnnounceCreate;

    @FindBy(id = "btn-header-manager")
    private FluentWebElement buttonHeaderManager;

    @FindBy(id = "btn-header-user-create")
    private FluentWebElement buttonHeaderUserCreate;

    @FindBy(id = "btn-header-user-login")
    private FluentWebElement buttonHeaderUserLogin;

    @FindBy(id = "btn-header-user-logoff")
    private FluentWebElement buttonHeaderUserLogoff;

    @FindBy(id = "text-header-user-name")
    private FluentWebElement textHeaderUserName;


    public GenericPage deleteCookieByName(String name) {
        this.getDriver().manage().deleteCookieNamed(name);
        return this;
    }

    public GenericPage verifyFieldValue(FluentWebElement field, String value) {
        await().atMost(15, TimeUnit.SECONDS).until(field).value(value);
        return this;
    }

    public GenericPage verifyAlertPresent(String className) {
        await().atMost(15, TimeUnit.SECONDS)
                .until($(className)).present();

        return this;
    }

    public void confirmModal() {
        await().atMost(15, TimeUnit.SECONDS)
                .until(el(".modal.show")).displayed();

        FluentWebElement modal = $(".modal.show").get(0);

        modal.$(".btn.btn-secondary").click();
    }
}
