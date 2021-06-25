package br.edu.ifrs.canoas.webapp.web.page.announce;

import br.edu.ifrs.canoas.webapp.web.page.ListPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@Data
@PageUrl("/manager/announces/active")
public class AnnounceListActivePage extends ListPage {
    public AnnounceListActivePage() {
        super(".callout.callout-pink.callout-announce");
    }
}
