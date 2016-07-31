package io.github.toandv.service;

import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.toandv.web.JwtSubject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenHandler {
	private final String secret;
	private final MyUserDetailsService accountService;

	public TokenHandler(String secret, MyUserDetailsService accountService) {
		this.secret = secret;
		this.accountService = accountService;
	}

	public User parseUserFromToken(String token) {
		String json = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		JwtSubject subject = fromson(json, JwtSubject.class);
		// TODO No need to load from db possibly
		return (User) accountService.loadUserByUsername(subject.getUsername());
	}

	public String createTokenForUser(JwtSubject subject) {
		return Jwts.builder().setSubject(toJson(subject)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	static ObjectMapper MAPPER = new ObjectMapper();

	public static String toJson(Object v) {
		try {
			return MAPPER.writeValueAsString(v);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromson(String json, Class<T> type) {
		try {
			return MAPPER.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
