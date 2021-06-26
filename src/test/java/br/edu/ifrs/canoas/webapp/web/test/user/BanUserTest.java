package br.edu.ifrs.canoas.webapp.web.test.user;

import br.edu.ifrs.canoas.webapp.web.config.BaseFluentTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class BanUserTest extends BaseFluentTest {

    @RetryingTest(5)
    public void testBanUser(){

    }

    @Test
    public void testAdminCanNotBanHimself(){

    }

    @Test
    public void testCreateUserCPFBanned(){

    }

    @Test
    public void testCreateUserUsernameBanned(){

    }

    @Test
    public void testUserCanNotBanUser(){

    }

    @Test
    public void testModeratorCanNotBanUser(){

    }
}
