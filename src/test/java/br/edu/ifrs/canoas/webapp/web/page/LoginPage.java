package br.edu.ifrs.canoas.webapp.web.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@PageUrl("http://localhost:{port}/user/profile")
public class LoginPage extends FluentPage {

    public void isAt() {
        assertThat(window().title()).isEqualTo("IFRS");
    }

}