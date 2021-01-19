package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.Comment;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.helper.CommentHelper;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import br.edu.ifrs.canoas.webapp.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentServiceTest extends BaseTest{
    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AnnounceRepository announceRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSave() {
        Comment comment = CommentHelper.createComment();
        comment.getAnnounce().setId(101L);
        comment.getUser().setId(100L);

        commentService.save(comment);

        assertThat(comment.getId()).isNotNull();

        assertThat(commentRepository.findById(comment.getId()).get())
                .isEqualTo(comment);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSaveExisting() {
        Long id = 101L;
        Comment comment = commentRepository.findById(id).get();

        comment.setMessage("new message");

        commentService.save(comment);

        assertThat(comment.getId()).isEqualTo(id);

        assertThat(commentRepository.findById(id).get().getMessage())
                .isEqualTo("new message");
    }

    @Test
    public void testFindByIdAnnounce() {
        Announce announce = announceRepository.findById(103L).get();

        Comment comment = commentService.findByIdAndAnnounce(101L, announce);

        assertThat(comment.getMessage()).isEqualTo("Boa noite gostaria de mais informações.");
    }

    @Test
    public void testFindAll() {
        PaginatedEntity<Comment> comments;
        Announce announce = announceRepository.findById(103L).get();

        comments = commentService.findAll(0, 8, announce, CommentStatus.ACTIVE);

        assertThat(comments.getTotalResults()).isEqualTo(2);
        assertThat(comments.getTotalPages()).isEqualTo(0);
        assertThat(comments.getCurrentPage()).isEqualTo(0);
        assertThat(comments.getData()).hasSize(2);

        assertThat(comments.getData().stream()
                    .allMatch((e) -> e.getStatus().equals(CommentStatus.ACTIVE)))
                .isTrue();

        assertThat(comments.getData().get(0).getId()).isEqualTo(102);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testRemove() {
        Comment comment = commentRepository.findById(101L).get();

        commentService.remove(comment);

        comment = commentRepository.findById(101L).get();

        assertThat(comment.getStatus()).isEqualTo(CommentStatus.DELETED);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSetStatus() {
        Comment comment = commentRepository.findById(101L).get();

        commentService.setStatus(comment, CommentStatus.DELETED);

        comment = commentRepository.findById(101L).get();

        assertThat(comment.getStatus()).isEqualTo(CommentStatus.DELETED);
    }

    @Test
    public void testCountAll() {
        Long count = commentService.countAll(CommentStatus.ACTIVE);
        assertThat(count).isEqualTo(2);
    }
}
