package com.jws.token.security.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "JWSUserSessions")
public class JWSUserSessions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7222185358586139923L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private JWSUsers user;

	@Column(nullable = false)
	private String sessionToken;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private final ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateLogout;

	@Column(nullable = false)
	private Boolean active;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public JWSUsers getUser() {
		return this.user;
	}

	public void setUser(final JWSUsers user) {
		this.user = user;
	}

	public String getSessionToken() {
		return this.sessionToken;
	}

	public void setSessionToken(final String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public ZonedDateTime getDateLogout() {
		return this.dateLogout;
	}

	public void setDateLogout(final ZonedDateTime dateLogout) {
		this.dateLogout = dateLogout;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public ZonedDateTime getDateCreated() {
		return this.dateCreated;
	}

}
