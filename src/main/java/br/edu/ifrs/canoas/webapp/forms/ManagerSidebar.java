package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.CommentService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
public class ManagerSidebar {
    private BarItem userProfile = new BarItem("/manager/user/profile");
    private BarItem userPassword = new BarItem("/manager/user/password");
    private BarItem announceActive = new BarItem("/manager/announces/active");
    private BarItem announceWaitingReview = new BarItem("/manager/announces/waiting-review");
    private BarItem announceBlocked = new BarItem("/manager/announces/blocked");
    private BarItem reportAnnounces = new BarItem("/manager/reports/announces");
    private BarItem reportComments = new BarItem("/manager/reports/comments");
    private BarItem adminUsers = new BarItem("/manager/admin/users");

    private boolean moderator = false;
    private boolean admin = false;

    public void set(AnnounceService announceService, CommentService commentService, HttpServletRequest request) {
        String uri = request.getRequestURI();

        this.moderator = Auth.hasRole(new Role[]{Role.ROLE_ADMIN, Role.ROLE_MODERATOR});
        this.admin = Auth.hasRole(new Role[]{Role.ROLE_ADMIN});

        userProfile.set(uri);
        userPassword.set(uri);
        announceActive.set(uri);
        announceWaitingReview.set(uri);
        announceBlocked.set(uri);
        reportAnnounces.set(uri);
        reportComments.set(uri);
        adminUsers.set(uri);

        announceWaitingReview.setSize(announceService.countAll(Auth.getUser(), AnnounceStatus.WAITING_REVIEW));

        if (this.moderator) {
            reportAnnounces.setSize(announceService.countAll(AnnounceStatus.WAITING_REVIEW));
            reportComments.setSize(commentService.countAll(CommentStatus.WAITING_REVIEW));
        }
    }


}
