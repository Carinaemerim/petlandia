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

    @FindBy(id = "blocked-announces-link")
    protected FluentWebElement blockedAnnouncesLink;

    @FindBy(id = "waiting-review-announces-link")
    protected FluentWebElement waitingReviewAnnouncesLink;

    @FindBy(id = "active-announces-link")
    protected FluentWebElement activeAnnouncesLink;

    @FindBy(id = "reported-announces-link")
    protected FluentWebElement reportedAnnouncesLink;

    @FindBy(id = "reported-comments-link")
    protected FluentWebElement reportedCommentsLink;

    @FindBy(id = "reported-users-link")
    protected FluentWebElement reportedUsersLink;

    @FindBy(id = "admin-user-link")
    protected FluentWebElement adminUsersLink;
}
