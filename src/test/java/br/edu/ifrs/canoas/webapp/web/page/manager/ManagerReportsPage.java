package br.edu.ifrs.canoas.webapp.web.page.manager;

import br.edu.ifrs.canoas.webapp.web.page.ListPage;
import org.fluentlenium.core.domain.FluentWebElement;

public class ManagerReportsPage extends ListPage {
    public ManagerReportsPage() {
        super(".callout.callout-pink");
    }

    public void acceptReport(int position) {
        FluentWebElement item = this.getItem(position);

        item.$(".btn-accept").first().click();
        this.confirmModal();
    }
    public void rejectReport(int position) {
        FluentWebElement item = this.getItem(position);

        item.$(".btn-reject").first().click();
        this.confirmModal();
    }
}
