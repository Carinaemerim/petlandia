package br.edu.ifrs.canoas.webapp.web.test;

import br.edu.ifrs.canoas.webapp.web.config.MyFluentTest;
import br.edu.ifrs.canoas.webapp.web.page.LoginPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;


public class LoginTest extends MyFluentTest {

    @Page
    LoginPage loginPage;

    @Test
    public void testSuccessfullyLogin(){

    }

    @Test
    public void testFailedLogin(){

    }

    @Test
    public void testLoginPersistence(){

    }



}