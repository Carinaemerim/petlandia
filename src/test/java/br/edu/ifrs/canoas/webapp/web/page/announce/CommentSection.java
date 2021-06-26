package br.edu.ifrs.canoas.webapp.web.page.announce;

import br.edu.ifrs.canoas.webapp.web.page.ListPage;
import lombok.Data;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

@Data
public class CommentSection extends ListPage {
    @FindBy(id = "modal-comment-report")
    protected FluentWebElement modalCommentReport;

    @FindBy(id = "announce_form_comment_report")
    protected FluentWebElement reportCommentReasonField;

    @FindBy(id = "report-comment-submit-btn")
    protected FluentWebElement reportCommentSubmitBtn;

    public CommentSection() {
        super(".direct-chat-msg");
    }

    public void removeComment(int position) {
        FluentWebElement comment = this.getItem(position);
        comment.$(".btn-comment-remove").first().click();
        this.confirmModal();
    }

    public void reportComment(int position, String reason) {
        FluentWebElement comment = this.getItem(position);
        comment.$(".btn-comment-report").first().click();

        await().atMost(15, TimeUnit.SECONDS).until(reportCommentReasonField).clickable();

        reportCommentReasonField.fill().withText(reason);

        reportCommentSubmitBtn.click();
    }

    public boolean hasCommentWithText(String text) {
        return $(this.itemSelector)
                .find(".direct-chat-text")
                .stream()
                .anyMatch((FluentWebElement e) -> e.text().contains(text));
    }
}
