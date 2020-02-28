package com.jws.token.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jws.token.security.entity.JWSUserSessions;
import com.jws.token.security.entity.JWSUsers;
import com.jws.token.security.model.UserDTO;
import com.jws.token.security.repository.UserSessionsRepository;
import com.jws.token.security.repository.UsersRepository;

@Service
public class JwtAuthenticationService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private UserSessionsRepository sessionRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * 
	 * @return List<UserDTO>
	 */
	public List<UserDTO> listAllUsers() {
		final List<UserDTO> userList = new ArrayList<>();

		this.userRepository.findAll().forEach(p -> {
			userList.add(new UserDTO(p.getId(), p.getName(), p.getUsername(), null));
		});

		return userList;
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public UserDTO loadUser(final String username) {
		final Optional<JWSUsers> findByUsernameOptional = this.userRepository.findByUsername(username);
		if (!findByUsernameOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		final JWSUsers users = findByUsernameOptional.get();
		return new UserDTO(users.getId(), users.getName(), users.getUsername(), users.getPassword());
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public JWSUsers save(final UserDTO user) {
		final JWSUsers newUser = new JWSUsers();
		newUser.setName(StringUtils.isEmpty(user.getName()) ? user.getUsername() : user.getName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(this.bcryptEncoder.encode(user.getPassword()));
		return this.userRepository.save(newUser);
	}

	/**
	 * 
	 * @param username
	 * @param token
	 */
	public void saveUserSession(final String username, final String token) {

		final JWSUserSessions session = new JWSUserSessions();

		session.setUser(this.userRepository.findByUsername(username).get());
		session.setSessionToken(token);
		session.setActive(true);

		this.sessionRepository.save(session);
	}
}
