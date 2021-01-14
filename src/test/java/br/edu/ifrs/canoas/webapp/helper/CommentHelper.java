package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.Comment;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;

import java.time.LocalDateTime;

public class CommentHelper {
    public static Comment createComment() {
        return Comment.builder()
                .status(CommentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(UserHelper.createUser())
                .announce(AnnounceHelper.createAnnounce())
                .message("Mensagem legal")
                .build();
    }
}
