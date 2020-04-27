package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

public class Auth {
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return true;
    }

    public static boolean hasRole(Role[] roles) {
        if (!isAuthenticated()) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            for (Role role : roles) {
                boolean result = role.name().equals(auth.getAuthority());

                if (result) {
                    return true;
                }
            }
        }

        return false;
    }

    public static User getUser() {
        if (!isAuthenticated()) {
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserImpl userImpl = (UserImpl) authentication.getPrincipal();
        User user = userImpl.getUser();
        return user;
    }
}
