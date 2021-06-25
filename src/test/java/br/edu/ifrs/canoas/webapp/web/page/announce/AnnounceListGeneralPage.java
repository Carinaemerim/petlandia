package br.edu.ifrs.canoas.webapp.web.page.announce;

import br.edu.ifrs.canoas.webapp.web.page.ListPage;
import org.fluentlenium.core.annotation.PageUrl;

@PageUrl("/announces")
public class AnnounceListGeneralPage extends ListPage {
    public AnnounceListGeneralPage() {
        super(".callout.callout-pink.callout-announce");
    }
}
