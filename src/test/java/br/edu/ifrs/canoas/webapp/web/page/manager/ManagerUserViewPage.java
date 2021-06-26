package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

@Data
@PageUrl("/manager/admin/users/{userId}")
public class ManagerUserViewPage extends GenericPage {
    @FindBy(id = "btn-report-user")
    protected FluentWebElement reportUserButton;

    @FindBy(id = "btn-block-user")
    protected FluentWebElement blockUserButton;

    @FindBy(id = "btn-edit-role")
    protected FluentWebElement editRoleButton;

    @FindBy(id = "user_form_report")
    protected FluentWebElement userReportReasonField;

    @FindBy(id = "modal-report-user")
    protected FluentWebElement reportUserModal;

    @FindBy(id = "submit-report-user-button")
    protected FluentWebElement submitReportUserButton;

    public void reportUser(String reason) {
        this.reportUserButton.click();

        await().atMost(15, TimeUnit.SECONDS).until(
            this.userReportReasonField
        ).clickable();

        this.userReportReasonField.fill().withText(reason);
        this.submitReportUserButton.click();
    }
}
