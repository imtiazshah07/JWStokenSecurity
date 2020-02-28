package com.jws.token.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jws.token.security.model.UserDTO;
import com.jws.token.security.service.JwtAuthenticationService;

/**
 * 
 *  
 */
@RestController
public class JwsUserController {

	@Autowired
	private JwtAuthenticationService service;

	/**
	 * 
	 */
	@RequestMapping({ "/listUsers" })
	public ResponseEntity<List<UserDTO>> listAllUsers() {
		return ResponseEntity.ok(this.service.listAllUsers());
	}
}
