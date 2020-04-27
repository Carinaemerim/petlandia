package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
public class ManagerSidebar {
    private ManagerSidebarItem userProfile = new ManagerSidebarItem("/manager/user/profile");
    private ManagerSidebarItem userPassword = new ManagerSidebarItem("/manager/user/password");
    private ManagerSidebarItem announceActive = new ManagerSidebarItem("/manager/announces/active");
    private ManagerSidebarItem announceWaitingReview = new ManagerSidebarItem("/manager/announces/waiting-review");
    private ManagerSidebarItem announceBlocked = new ManagerSidebarItem("/manager/announces/blocked");
    private ManagerSidebarItem denunciationAnnounces = new ManagerSidebarItem("/manager/denunciation/announces");
    private ManagerSidebarItem denunciationComments = new ManagerSidebarItem("/manager/denunciation/comments");
    private ManagerSidebarItem adminUsers = new ManagerSidebarItem("/manager/admin/users");

    private boolean moderator = true;
    private boolean admin = true;

    public void set(AnnounceService service, HttpServletRequest request) {
        String uri = request.getRequestURI();

        //this.moderator = Auth.hasRole(new Role[]{Role.ADMIN, Role.MODERATOR});
        //this.admin = Auth.hasRole(new Role[]{Role.ADMIN});

        userProfile.set(uri);
        userPassword.set(uri);
        announceActive.set(uri);
        announceWaitingReview.set(uri);
        announceBlocked.set(uri);
        denunciationAnnounces.set(uri);
        denunciationComments.set(uri);
        adminUsers.set(uri);

        announceWaitingReview.setSize(service.countAll(Auth.getUser(), AnnounceStatus.WAITING_REVIEW));

        if (this.moderator) {
            // TODO: Pegar da base de dados
            denunciationAnnounces.setSize(2l);
            denunciationComments.setSize(3l);
        }
    }


}
