package com.jws.token.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jws.token.security.config.JwtTokenUtil;
import com.jws.token.security.model.JwtRequest;
import com.jws.token.security.model.JwtResponse;
import com.jws.token.security.model.UserDTO;
import com.jws.token.security.service.JwtAuthenticationService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtAuthenticationService jwtAuthenticationService;

	/**
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest)
			throws Exception {
		this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDTO userDto = this.jwtAuthenticationService.loadUser(authenticationRequest.getUsername());
		final String token = this.jwtTokenUtil.generateToken(userDto);
		this.jwtAuthenticationService.saveUserSession(userDto.getUsername(), token);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody final UserDTO user) throws Exception {
		return ResponseEntity.ok(this.jwtAuthenticationService.save(user));
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(final String username, final String password) throws Exception {
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (final DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (final BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
