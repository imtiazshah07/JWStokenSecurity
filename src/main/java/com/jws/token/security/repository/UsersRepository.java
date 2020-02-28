package com.jws.token.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jws.token.security.entity.JWSUsers;

@Repository
public interface UsersRepository extends CrudRepository<JWSUsers, Long> {

	public Optional<JWSUsers> findByUsername(String username);
}
