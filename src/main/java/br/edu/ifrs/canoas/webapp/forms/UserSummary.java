package br.edu.ifrs.canoas.webapp.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
    Long reportedAnnouncesAccepted = 0L;
    Long reportedAnnouncesRejected = 0L;
    Long reportedCommentsAccepted = 0L;
    Long reportedCommentsRejected = 0L;
    Long reportedUsersAccepted = 0L;
    Long reportedUsersRejected = 0L;
}
