package io.github.toandv.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import io.github.toandv.web.JwtSubject;

public class TokenAuthenticationService {
	private static final String AUTH_HEADER_NAME = "Authorization";

	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, MyUserDetailsService userService) {
		tokenHandler = new TokenHandler(secret, userService);
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(new JwtSubject(user)));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final User user = tokenHandler.parseUserFromToken(token);
			if (user != null) {
				return new UserAuthentication(user);
			}
		}
		return null;
	}

	public String createTokenForUser(JwtSubject subject) {
		return tokenHandler.createTokenForUser(subject);
	}

}
