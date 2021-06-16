package br.edu.ifrs.canoas.webapp;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.helper.ScoreHelper;
import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;
import lombok.Getter;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Component
@Getter
public class MockAuthContext {

    public static Long USER_ID = 1000L;

    protected UserImpl userDetails;
    protected User user;

    /**
     * Utilizar @MockBean deste bean na classe que for utilizar MockAuthContext
     */
    @Autowired
    UserDetailsImplService implService;

    public void tearDown() {
        this.user = null;
        this.userDetails = null;
    }

    public void mockAuthAdmin() {
        final Long adminId = USER_ID;
        final String adminName = "usuário admin";
        final String adminEmail = "admin@petlandia.com";
        final String adminUsername = "admin";
        final String adminPassword = "user";
        final Role adminRole = Role.ROLE_ADMIN;

        mockAuth(adminId, adminEmail, adminName, adminUsername, adminPassword, adminRole);
    }

    public void mockAuthModerator() {
        final Long moderatorId = USER_ID;
        final String moderatorName = "usuário moderador";
        final String moderatorEmail = "moderador@petlandia.com";
        final String moderatorUsername = "moderador";
        final String moderatorPassword = "user";
        final Role moderatorRole = Role.ROLE_MODERATOR;

        mockAuth(moderatorId, moderatorEmail, moderatorName, moderatorUsername, moderatorPassword, moderatorRole);
    }

    public void mockAuthUser() {
        final Long userId = USER_ID;
        final String userName = "usuário autenticado";
        final String userEmail = "user@petlandia.com";
        final String userUsername = "user";
        final String userPassword = "user";
        final Role userRole = Role.ROLE_USER;

        mockAuth(userId, userEmail, userName, userUsername, userPassword, userRole);
    }

    public void mockAuthAnonymous() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(new AnonymousAuthenticationToken(
                "guest", "guest", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
        ));
        Mockito.when(implService.loadUserByUsername(any(String.class))).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);
    }

    private void mockAuth(Long userId, String userEmail, String name, String username, String password,
                          Role role) {
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(role.name());

        user = new User();
        user.setId(userId);
        user.setEmail(userEmail);
        user.setName(name);
        user.setPassword(password);
        user.setUsername(username);
        user.setRole(role);

        user.setAnimalType(ScoreHelper.createScore("DOG", 2.0, AnimalType.class));
        user.setAnimalCastrated(ScoreHelper.createScore("YES", 2.0, AnimalCastrated.class));
        user.setAnimalGender(ScoreHelper.createScore("FEMALE", 2.0, AnimalGender.class));
        user.setAnimalAge(ScoreHelper.createScore("PUPPY", 2.0, AnimalAge.class));
        user.setAnimalColor(ScoreHelper.createScore("WHITE", 2.0, AnimalColor.class));
        user.setAnimalSize(ScoreHelper.createScore("MEDIUM", 2.0, AnimalSize.class));

        userDetails = new UserImpl(
                username,
                password,
                authorityList,
                user
        );

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(userDetails);
        Mockito.when(implService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getAuthorities()).thenAnswer(x -> authorityList);
        SecurityContextHolder.setContext(securityContext);
    }

}
