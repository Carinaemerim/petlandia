package br.edu.ifrs.canoas.webapp.config.auth;

import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Reload auth on every request to non public content.
 * <p>
 * This ensures that the user session is always updated.
 * For example suppose an ADMIN deactivates a moderator. The changes will
 * apply on the next request that moderator makes.
 * Without this, those changes would only be applied when the moderator
 * logged in and out of the system.
 */
@Component
@AllArgsConstructor
public class VerifyAccessInterceptor implements HandlerInterceptor {

    private final UserDetailsImplService userDetailsImplService;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return true;
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsImplService.loadUserByUsername(auth.getName());
        } catch (UsernameNotFoundException e) {
            userDetails = null;
        }

        if (userDetails == null) {
            Map<String, ?> lastAttributes = RequestContextUtils.getInputFlashMap(request);
            new SecurityContextLogoutHandler().logout(request, null, null);
            FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);

            FlashMap flashMap = new FlashMap();
            flashMap.putAll(lastAttributes);

            if (flashMapManager != null) {
                flashMapManager.saveOutputFlashMap(flashMap, request, response);
            }

            response.sendRedirect("/login");
            return false;
        }

        Authentication newAuth = null;

        if (auth.getClass() == UsernamePasswordAuthenticationToken.class) {

            if (auth.getPrincipal() != null) {
                newAuth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            }
        }

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return true;
    }

}

