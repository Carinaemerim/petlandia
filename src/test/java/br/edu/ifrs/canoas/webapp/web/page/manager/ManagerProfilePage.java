package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/manager/user/profile")
@Data
public class ManagerProfilePage extends GenericPage {
    @FindBy(id = "btn-edit-user")
    private FluentWebElement btnEditUser;

    @FindBy(id = "user_form_state")
    private FluentWebElement stateFormField;
}
