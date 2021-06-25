package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/manager/user/profile/edit")
@Data
public class ManagerProfileEditPage extends GenericPage {
    @FindBy(id = "btn-submit")
    private FluentWebElement btnSubmit;

    @FindBy(id = "user_form_state")
    private FluentWebElement stateFormField;
}
