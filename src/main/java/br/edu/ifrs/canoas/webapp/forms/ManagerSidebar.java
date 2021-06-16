package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.enums.UserStatus;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.CommentService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
public class ManagerSidebar {
    private final BarItem userProfile = new BarItem("/manager/user/profile");
    private final BarItem userPassword = new BarItem("/manager/user/password");
    private final BarItem announceActive = new BarItem("/manager/announces/active");
    private final BarItem announceWaitingReview = new BarItem("/manager/announces/waiting-review");
    private final BarItem announceBlocked = new BarItem("/manager/announces/blocked");
    private final BarItem reportAnnounces = new BarItem("/manager/reports/announces");
    private final BarItem reportComments = new BarItem("/manager/reports/comments");
    private final BarItem reportUsers = new BarItem("/manager/reports/users");
    private final BarItem adminUsers = new BarItem("/manager/admin/users");

    private boolean moderator = false;

    public void set(AnnounceService announceService, CommentService commentService, UserService userService, HttpServletRequest request) {
        String uri = request.getRequestURI();

        this.moderator = Auth.hasRole(new Role[]{Role.ROLE_ADMIN, Role.ROLE_MODERATOR});

        userProfile.set(uri);
        userPassword.set(uri);
        announceActive.set(uri);
        announceWaitingReview.set(uri);
        announceBlocked.set(uri);
        reportAnnounces.set(uri);
        reportComments.set(uri);
        reportUsers.set(uri);
        adminUsers.set(uri);

        announceWaitingReview.setSize(announceService.countAll(Auth.getUser(), AnnounceStatus.WAITING_REVIEW));

        if (this.moderator) {
            reportAnnounces.setSize(announceService.countAll(AnnounceStatus.WAITING_REVIEW));
            reportComments.setSize(commentService.countAll(CommentStatus.WAITING_REVIEW));
            reportUsers.setSize(userService.countAll(UserStatus.WAITING_REVIEW));
        }
    }


}
