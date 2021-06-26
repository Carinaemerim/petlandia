package br.edu.ifrs.canoas.webapp.web.page;

import org.fluentlenium.core.annotation.PageUrl;

import java.util.concurrent.TimeUnit;

@PageUrl("/login")
public class LoginPage extends GenericPage {
    private static final String LOGIN_FORM = "#login-user-form";

    public LoginPage fillAndSubmitFormAwait(String... paramsOrdered) {
        this.deleteCookieByName("JSESSIONID");
        this.fillAndSubmitForm(paramsOrdered);
        this.awaitUntilFormDisappear();
        return this;
    }


    public LoginPage fillAndSubmitForm(String... paramsOrdered) {
        $("input").fill().with(paramsOrdered);
        $("#submit-button").submit();
        return this;
    }

    public LoginPage awaitUntilFormDisappear() {
        await().atMost(15, TimeUnit.SECONDS).until(el(LOGIN_FORM)).not().present();
        return this;
    }



}