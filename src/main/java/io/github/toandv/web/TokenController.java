package io.github.toandv.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.toandv.domain.user.UserRepository;
import io.github.toandv.service.TokenAuthenticationService;

@RestController
@RequestMapping("/v1/api")
public class TokenController {
	public static String contentType = "application/json";

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenAuthenticationService service;

	@PostMapping(path = "/auth/token", headers = { "Content-Type=application/json" })
	public JwtResponse authToken(@RequestBody UserInfo userInfo) {
		if (userInfo == null) {
			throw new UsernameNotFoundException("Not Found");
		}
		io.github.toandv.domain.user.User user = userRepository.findByUsernameAndPassword(userInfo.getUsername(),
				userInfo.getPassword());
		if (user == null) {
			throw new UsernameNotFoundException("Not Found.");
		}
		String jwtToken = service.createTokenForUser(new JwtSubject(user.getUsername(), user.roleNames()));

		return new JwtResponse(user.getUsername(), user.roleNames(), jwtToken, new Date());
	}

}
