package br.edu.ifrs.canoas.webapp.web.page.announce;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

@PageUrl("/announces/{announceId}")
@Data
public class AnnounceViewPage extends GenericPage {
    @FindBy(id = "announce-title")
    private FluentWebElement announceTitle;

    @FindBy(id = "edit-announce-btn")
    protected FluentWebElement editAnnounceBtn;

    @FindBy(id = "delete-announce-btn")
    protected FluentWebElement deleteAnnounceBtn;

    @FindBy(id = "comment-submit-button")
    protected FluentWebElement commentSubmitBtn;

    @FindBy(id = "comment-content-input")
    protected FluentWebElement commentContentInput;

    @FindBy(id = "modal-announce-report")
    protected FluentWebElement modalAnnounceReport;

    @FindBy(id = "report-announce-btn")
    protected FluentWebElement reportAnnounceBtn;

    @FindBy(id = "announce_form_report")
    protected FluentWebElement announceReportReasonField;

    @FindBy(id = "submit-report-btn")
    protected FluentWebElement submitReportButton;

    public void reportAnnounce(String reason) {
        reportAnnounceBtn.click();

        await().atMost(15, TimeUnit.SECONDS)
                .until(modalAnnounceReport).displayed();

        announceReportReasonField.fill().withText(reason);

        submitReportButton.click();
    }

}
