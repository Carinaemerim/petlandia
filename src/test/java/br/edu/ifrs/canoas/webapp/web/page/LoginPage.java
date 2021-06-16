package br.edu.ifrs.canoas.webapp.web.page;

import org.fluentlenium.core.annotation.PageUrl;

@PageUrl("/login")
public class LoginPage extends GenericPage {

    public LoginPage fillAndSubmitFormAwait(String... paramsOrdered) {
        this.deleteCookieByName("JSESSIONID");
        this.fillAndSubmitForm(paramsOrdered);
        return this;
    }


    public LoginPage fillAndSubmitForm(String... paramsOrdered) {
        $("input").fill().with(paramsOrdered);
        $("#submit-button").submit();
        return this;
    }


}