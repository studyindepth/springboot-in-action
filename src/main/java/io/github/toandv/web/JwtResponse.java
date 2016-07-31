package io.github.toandv.web;

import java.util.Collection;
import java.util.Date;

import io.github.toandv.domain.user.Role;
import io.github.toandv.domain.user.Role.RoleName;

public class JwtResponse {

	private String username;

	private Collection<RoleName> roles;

	private String jwtToken;

	// Requires later.
	private Date expiration;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Collection<RoleName> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleName> roles) {
		this.roles = roles;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public JwtResponse() {
	}

	public JwtResponse(String username, Collection<RoleName> roles, String jwtToken, Date expiration) {
		this.username = username;
		this.roles = roles;
		this.jwtToken = jwtToken;
		this.expiration = expiration;
	}

}
