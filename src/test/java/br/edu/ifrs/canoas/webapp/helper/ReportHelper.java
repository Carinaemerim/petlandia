package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.Report;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportType;

import java.time.LocalDateTime;

public class ReportHelper {
    public static Report createReport() {
        return Report.builder()
                .status(ReportStatus.ACCEPTED)
                .type(ReportType.COMMENT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .ratedAt(LocalDateTime.now())
                .ratedBy(UserHelper.createUser())
                .reportBy(UserHelper.createUser())
                .announce(AnnounceHelper.createAnnounce())
                .comment(CommentHelper.createComment())
                .message("Mensagem legal")
                .build();
    }
}
