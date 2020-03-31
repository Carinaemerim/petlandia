package br.edu.ifrs.canoas.webapp.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void save(User user) {

		userRepository.save(user);
	}

	public User getOne(User user) {
		Optional<User> optUser = userRepository.findById(user.getId());
		return optUser.isPresent()?optUser.get():null;
	}


	public List<User> listUser() {
//	    if (searchTerm == null || searchTerm.trim().length() == 0){
//	        return userRepository.findAll();
//        }
//		return userRepository.findAllByUsernameContains(searchTerm);

		return userRepository.findAll();
	}


}
