package br.edu.ifrs.canoas.webapp.config;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    CustomSavedRequestAwareAuthenticationSuccessHandler() {
        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.setContextRelative(true);
        this.setRedirectStrategy(redirectStrategy);
    }
}
