package br.edu.ifrs.canoas.webapp.web.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.fluentlenium.core.domain.FluentWebElement;

@Data
@NoArgsConstructor
public class ListPage extends GenericPage {
    protected String itemSelector;

    public ListPage(String itemSelector) {
        this.itemSelector = itemSelector;
    }

    public FluentWebElement getItem(int position) {
        return $(itemSelector).get(position);
    }

    public void clickItem(int position) {
        this.getItem(position).click();
    }

    public void clickItemElement(int position, String elementSelector) {
        FluentWebElement item = this.getItem(position);

        item.$(elementSelector).first().click();
    }
}
