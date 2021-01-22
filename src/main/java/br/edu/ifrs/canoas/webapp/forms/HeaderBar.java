package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.helper.Auth;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
public class HeaderBar {
    private final BarItem home = new BarItem(null);
    private final BarItem announces = new BarItem("/announces");
    private final BarItem createAnnounce = new BarItem("/announce/create");
    private final BarItem createUser = new BarItem("/user/create");
    private final BarItem manager = new BarItem("/manager");
    private final BarItem suggestions = new BarItem("/suggestions");

    private boolean logged = false;

    public void set(HttpServletRequest request) {
        String uri = request.getRequestURI();

        this.logged = Auth.isAuthenticated();

        home.set(uri);
        announces.set(uri);
        createAnnounce.set(uri);
        createUser.set(uri);
        manager.set(uri);
        suggestions.set(uri);
    }


}
