package com.jws.token.security.config;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jws.token.security.entity.JWSUsers;
import com.jws.token.security.repository.UsersRepository;

/**
 * 
 *
 */
@Component
public class JwsTokenUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final Optional<JWSUsers> findByUsernameOptional = this.userRepository.findByUsername(username);
		if (!findByUsernameOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		final JWSUsers users = findByUsernameOptional.get();
		return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),
				new ArrayList<>());
	}
}
