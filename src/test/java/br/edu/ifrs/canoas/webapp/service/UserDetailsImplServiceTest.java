package br.edu.ifrs.canoas.webapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserDetailsImplServiceTest extends BaseTest {
    @Autowired
    UserDetailsImplService userDetailsImplService;

    @Test
    public void testLoadUserByUsername() {
        UserDetails user = userDetailsImplService.loadUserByUsername("admin");
        assertThat(user.getUsername()).isEqualTo("admin");
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> {
                    userDetailsImplService.loadUserByUsername("usuario_nao_existe");
                });
    }
}
