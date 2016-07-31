package io.github.toandv.web;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;

import io.github.toandv.domain.user.Role;
import io.github.toandv.domain.user.Role.RoleName;

public class JwtSubject {
	private String username;

	private Collection<RoleName> roles;

	public JwtSubject() {
	}

	public JwtSubject(String username, Collection<RoleName> roles) {
		this.username = username;
		this.roles = roles;
	}

	public JwtSubject(User user) {
		this.username = user.getUsername();
		this.roles = user.getAuthorities().stream().map(a -> Role.RoleName.valueOf(a.getAuthority()))
				.collect(Collectors.toList());
	}

	public String getUsername() {
		return username;
	}

	public Collection<RoleName> getRoles() {
		return roles;
	}

}
