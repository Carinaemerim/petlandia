package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.dao.AnnounceDao;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.ForbiddenException;
import br.edu.ifrs.canoas.webapp.forms.AnnounceFilterForm;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import br.edu.ifrs.canoas.webapp.repository.AnnounceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnnounceService {

    private static final int PAGE_LENGTH = 8;

    private final AnnounceRepository announceRepository;
    private final AnnounceDao announceDao;

    public Announce findById(Long id) {
        return announceRepository.findById(id).orElseThrow(AnnounceNotFoundException::new);
    }

    public Announce findByIdAndStatusActive(Long id) {
        return announceRepository.findAllByIdAndStatusActive(id)
                .orElseThrow(AnnounceNotFoundException::new);
    }

    public Announce findByIdAndCheck(Long id) {
        boolean isModerator = Auth.hasRole(new Role[]{Role.ROLE_MODERATOR, Role.ROLE_ADMIN});
        Announce announce = announceRepository.findById(id)
                .orElseThrow(AnnounceNotFoundException::new);;

        if (!isModerator && !announce.getStatus().equals(AnnounceStatus.ACTIVE)) {
            throw new AnnounceNotFoundException();
        }
        return announce;
    }

    public Announce save(Announce announce) {
        return announceRepository.save(announce);
    }

    public PaginatedEntity<Announce> findAll(int pageNumber, User user, AnnounceStatus status) {
        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH);
        Page<Announce> announcePage = announceRepository.findAllByStatusAndUserOrderByCreatedAtDescIdDesc(status, user, page);
        return PaginatedEntity.<Announce>builder()
                .currentPage(pageNumber)
                .data(announcePage.getContent())
                .totalResults(announcePage.getTotalElements())
                .pageLength(PAGE_LENGTH)
                .build();
    }

    public PaginatedEntity<Announce> findAll(int pageNumber, AnnounceStatus status) {
        Pageable page = PageRequest.of(pageNumber, PAGE_LENGTH);
        Page<Announce> announcePage = announceRepository.findAllByStatusOrderByCreatedAtDescIdDesc(status, page);

        return PaginatedEntity.<Announce>builder()
                .currentPage(pageNumber)
                .data(announcePage.getContent())
                .totalResults(announcePage.getTotalElements())
                .pageLength(PAGE_LENGTH)
                .build();
    }

    public List<Announce> findFirstFive(AnnounceStatus status) {
        return announceRepository.findFirst5ByStatusEqualsOrderByCreatedAtDescIdDesc(status);
    }

    public Long countAll(User user, AnnounceStatus status) {
        return announceRepository.countAllByStatusAndUser(status, user);
    }

    public Long countAll(AnnounceStatus status) {
        return announceRepository.countAllByStatus(status);
    }

    public void setStatus(Announce announce, AnnounceStatus status) {
        announce.setStatus(status);
        announceRepository.save(announce);
    }

    public PaginatedEntity<Announce> findAll(AnnounceFilterForm filters, AnnounceStatus status, int pageLength) {
        Long count = announceDao.countAllByFilter(filters, status);
        List<Announce> list = announceDao.findAllByFilter(filters, status, pageLength);

        return PaginatedEntity.<Announce>builder()
                .currentPage(filters.getPage())
                .data(list)
                .totalResults(count)
                .pageLength(pageLength)
                .build();
    }

    public void removeAnnounce(Long id) {
        Announce announce = this.findByIdAndStatusActive(id);

        if (!announce.canRemove()) {
            throw new ForbiddenException("Cannot remove announce");
        }

        announce.setStatus(AnnounceStatus.INACTIVE);
        announceRepository.save(announce);
    }
}
