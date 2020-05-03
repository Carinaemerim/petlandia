package br.edu.ifrs.canoas.webapp.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
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
						roleToCollection(user),
						user)
                ).orElseThrow(() -> new UsernameNotFoundException("couldn't find " + username + "!"));
	}

	private Collection<? extends GrantedAuthority> roleToCollection(User user) {
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(user.getRole().name()));
		return list.stream().collect(Collectors.toList());
	}
}