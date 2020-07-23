package br.edu.ifrs.canoas.webapp.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifrs.canoas.webapp.domain.PaginatedEntity;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void save(User user) {
		if (user.getEmail() != null) {
			user.setAvatar(user.getAvatarHash());
		}

		if(user.getId() == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		userRepository.save(user);
	}

	public User getOne(User user) {
		Optional<User> optUser = userRepository.findById(user.getId());
		return optUser.orElse(null);
	}


	public List<User> listUser() {
//	    if (searchTerm == null || searchTerm.trim().length() == 0){
//	        return userRepository.findAll();
//        }
//		return userRepository.findAllByUsernameContains(searchTerm);

		return userRepository.findAll();
	}

	public User findById(Long id){
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
	}

	public PaginatedEntity<User> findAll(int pageNumber, int pageLenght, String term){
		Pageable page = PageRequest.of(pageNumber, pageLenght);
		Page<User> userPage = userRepository.findAllByNameContainsIgnoreCaseOrCpfContainingOrUsernameIgnoreCaseContainingOrEmailContainingIgnoreCaseOrderByNameDescIdDesc(page, term, term, term, term);

		return PaginatedEntity.<User>builder()
				.currentPage(pageNumber)
				.data(userPage.getContent())
				.totalResults(userPage.getTotalElements())
				.pageLength(pageLenght)
				.build();
	}

}
