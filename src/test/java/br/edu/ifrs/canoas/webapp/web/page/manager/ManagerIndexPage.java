package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/manager")
@Data
public class ManagerIndexPage extends GenericPage {
    @FindBy(id = "sidebar-user-my-data")
    private FluentWebElement sidebarUserMyDataLink;
}
