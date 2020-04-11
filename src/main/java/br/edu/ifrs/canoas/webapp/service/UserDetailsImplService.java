package br.edu.ifrs.canoas.webapp.service;


import java.util.stream.Collectors;

import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsImplService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username)
				.map(user -> new UserImpl(
				        user.getUsername(),
                        user.getPassword(),
						user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()),
                        user)
                ).orElseThrow(() -> new UsernameNotFoundException("couldn't find " + username + "!"));
	}
}