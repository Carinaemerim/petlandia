package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.AnnounceSuggested;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SuggestionService {

    private final AnnounceRepository announceRepository;

    public List<Announce> findFirstFive(User user) {
        Pageable page = PageRequest.of(0, 5);
        return this.findAllByUser(user, AnnounceStatus.ACTIVE, page).getData();
    }

    public PaginatedEntity<Announce> findAllByUser(User user, AnnounceStatus status, Pageable pageable) {
        List<AnnounceSuggested> announcesSuggested = announceRepository.findAllSuggestedByUser(user, status, pageable);
        List<Announce> announces = new ArrayList<>();
        Long suggestionCount = announceRepository.countSuggestedByUser(user, status);

        for (AnnounceSuggested announceSuggested : announcesSuggested) {
            System.out.print("Announce: " + announceSuggested.getId());
            System.out.println(" Score: " + announceSuggested.getScore());

            Announce announce = announceRepository.findById(announceSuggested.getId())
                    .orElseThrow(AnnounceNotFoundException::new);
            announce.setScore(announceSuggested.getScore());
            announces.add(announce);
        }

        return PaginatedEntity.<Announce>builder()
                .currentPage(pageable.getPageNumber())
                .data(announces)
                .totalResults(suggestionCount)
                .pageLength(pageable.getPageSize())
                .build();
    }
}
