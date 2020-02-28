package com.jws.token.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jws.token.security.entity.JWSUserSessions;

@Repository
public interface UserSessionsRepository extends CrudRepository<JWSUserSessions, Long> {

	public Optional<JWSUserSessions> findBySessionToken(final String sessionToken);
}
