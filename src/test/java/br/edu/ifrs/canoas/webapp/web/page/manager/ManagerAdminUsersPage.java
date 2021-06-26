package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.ListPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;

@Data
@PageUrl("/manager/admin/users")
public class ManagerAdminUsersPage extends ListPage {
    public ManagerAdminUsersPage() {
        super(".user-item");
    }

    public void viewUser(int position) {
        FluentWebElement item = this.getItem(position);
        item.find(".user-action-link").click();
    }
}
