package com.jws.token.security.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jws.token.security.model.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2591704128475103648L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * 
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(final String token) {
		return this.getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(final String token) {
		return this.getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
		final Claims claims = this.getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(final String token) {
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(final String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateToken(final UserDTO userDto) {
		final Map<String, Object> claims = new HashMap<>();
		return this.doGenerateToken(claims, userDto.getUsername());
	}

	/**
	 * while creating the token - 1. Define claims of the token, like Issuer,
	 * Expiration, Subject, and the ID 2. Sign the JWT using the HS512 algorithm and
	 * secret key. 3. According to JWS Compact
	 * Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	 * compaction of the JWT to a URL-safe string
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(final Map<String, Object> claims, final String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}

	/**
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(final String token, final UserDetails userDetails) {
		final String username = this.getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
	}
}
