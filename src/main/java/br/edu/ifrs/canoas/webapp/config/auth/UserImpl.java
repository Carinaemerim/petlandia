package br.edu.ifrs.canoas.webapp.config.auth;

import br.edu.ifrs.canoas.webapp.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserImpl extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1308839480601748734L;

    private User user;

    public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserImpl(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public User getUser() {
        return user;
    }
}
