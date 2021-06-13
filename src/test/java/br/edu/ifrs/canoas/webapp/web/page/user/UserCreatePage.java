package br.edu.ifrs.canoas.webapp.web.page.user;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Getter;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@Getter
@PageUrl("/user/create")
public class UserCreatePage extends GenericPage {
    @FindBy(id = "#btn-home-user-create")
    private FluentWebElement buttonHomeUserCreate;

    @FindBy(id = "#btn-home-user-login")
    private FluentWebElement buttonHomeUserLogin;
}