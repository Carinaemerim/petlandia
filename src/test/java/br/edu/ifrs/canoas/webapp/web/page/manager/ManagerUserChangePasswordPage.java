package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@Data
@PageUrl("/manager/user/password")
public class ManagerUserChangePasswordPage extends GenericPage {
    @FindBy(id = "user_form_password_current")
    protected FluentWebElement currentPasswordField;

    @FindBy(id = "user_form_password_new")
    protected FluentWebElement newPasswordField;

    @FindBy(id = "user_form_password_repeat")
    protected FluentWebElement repeatPasswordField;

    @FindBy(id = "submit-change-password-button")
    protected FluentWebElement submitChangePasswordButton;

    public void fillChangePasswordForm(
        String currentPassword,
        String newPassword,
        String newPasswordConfirm
    ) {
        this.currentPasswordField.fill().withText(currentPassword);
        this.newPasswordField.fill().withText(newPassword);
        this.repeatPasswordField.fill().withText(newPasswordConfirm);
    }
}
