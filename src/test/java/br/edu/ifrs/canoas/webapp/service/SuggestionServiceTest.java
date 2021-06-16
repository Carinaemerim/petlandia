package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SuggestionServiceTest extends BaseTest{
    @Autowired
    SuggestionService suggestionService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindFirstFive() {
        User user = userRepository.findById(101L).get();

        List<Announce> announces = suggestionService.findFirstFive(user);

        assertThat(announces).hasSize(5);

        assertThat(announces.stream()
                .allMatch((e) -> e.getStatus().equals(AnnounceStatus.ACTIVE))).isTrue();

        // fail("Implementar checagens do algoritmo");
    }

    @Test
    public void testFindAllByUser() {
        User user = userRepository.findById(101L).get();
        Pageable pageable = PageRequest.of(0, 10);

        PaginatedEntity<Announce> announces;

        announces = this.suggestionService.findAllByUser(user, AnnounceStatus.ACTIVE, pageable);

        assertThat(announces.getCurrentPage()).isEqualTo(0);
        assertThat(announces.getTotalPages()).isEqualTo(3);
        assertThat(announces.getPageLength()).isEqualTo(10);
        assertThat(announces.getTotalPages()).isEqualTo(3);
        assertThat(announces.getData()).hasSize(10);

        assertThat(announces.getData().stream()
                .allMatch((e) -> e.getStatus().equals(AnnounceStatus.ACTIVE))).isTrue();

        fail("Implementar checagens do algoritmo");
    }
}
