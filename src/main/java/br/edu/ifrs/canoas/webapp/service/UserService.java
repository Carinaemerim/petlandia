package br.edu.ifrs.canoas.webapp.service;

import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.Report;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import br.edu.ifrs.canoas.webapp.enums.ReportType;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.enums.UserStatus;
import br.edu.ifrs.canoas.webapp.exception.UserNotFoundException;
import br.edu.ifrs.canoas.webapp.forms.UserSummary;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReportService reportService;

    public void delete(User user) {
        user.setStatus(UserStatus.DELETED);
        user.setRole(Role.ROLE_USER);

        Report[] reports = reportService.findAllByStatusAndTypeAndUser(ReportStatus.WAITING_REVIEW, ReportType.USER, user);
        for (Report report : reports) {
            report.setStatus(ReportStatus.ACCEPTED);
            reportService.save(report);
        }

        userRepository.save(user);
    }

    public User save(User user) {
        if (user.getEmail() != null) {
            user.setAvatar(user.getAvatarHash());
        }

        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User getOne(User user) {
        Optional<User> optUser = userRepository.findById(user.getId());
        return optUser.orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public PaginatedEntity<User> findAll(int pageNumber, int pageLenght, String term) {
        Pageable page = PageRequest.of(pageNumber, pageLenght);
        Page<User> userPage = userRepository.findAllByNameContainsIgnoreCaseOrCpfContainingOrUsernameIgnoreCaseContainingOrEmailContainingIgnoreCaseOrderByNameDescIdDesc(page, term, term, term, term);

        return PaginatedEntity.<User>builder()
                .currentPage(pageNumber)
                .data(userPage.getContent())
                .totalResults(userPage.getTotalElements())
                .pageLength(pageLenght)
                .build();
    }

    public UserSummary getSummary(User user) {
        return new UserSummary(
                userRepository.countReportedByTypeAndStatus(user, ReportType.ANNOUNCE, ReportStatus.ACCEPTED),
                userRepository.countReportedByTypeAndStatus(user, ReportType.ANNOUNCE, ReportStatus.REJECTED),
                userRepository.countReportedByTypeAndStatus(user, ReportType.COMMENT, ReportStatus.ACCEPTED),
                userRepository.countReportedByTypeAndStatus(user, ReportType.COMMENT, ReportStatus.REJECTED),
                userRepository.countReportedByTypeAndStatus(user, ReportType.USER, ReportStatus.ACCEPTED),
                userRepository.countReportedByTypeAndStatus(user, ReportType.USER, ReportStatus.REJECTED)
        );
    }

    public Long countAll(UserStatus status) {
        return userRepository.countAllByStatus(status);
    }

    public User findByIdByStatus(Long id, UserStatus status) {
        return userRepository.findByIdAndStatus(id, status)
            .orElseThrow(UserNotFoundException::new);
    }
}
