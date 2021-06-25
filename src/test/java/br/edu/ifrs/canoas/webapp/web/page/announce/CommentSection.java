package br.edu.ifrs.canoas.webapp.web.page.announce;

import br.edu.ifrs.canoas.webapp.web.page.ListPage;
import lombok.Data;
import org.fluentlenium.core.domain.FluentWebElement;

@Data
public class CommentSection extends ListPage {
    public CommentSection() {
        super(".direct-chat-msg");
    }

    public void removeComment(int position) {
        FluentWebElement comment = this.getItem(position);
        comment.$(".btn-comment-remove").first().click();
        this.confirmModal();
    }

    public boolean hasCommentWithText(String text) {
        return $(this.itemSelector)
                .find(".direct-chat-text")
                .stream()
                .anyMatch((FluentWebElement e) -> e.text().contains(text));
    }
}
