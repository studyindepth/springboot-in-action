package io.github.toandv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.toandv.domain.user.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		io.github.toandv.domain.user.User user = repo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return new User(user.getUsername(), user.getPassword(), user.rolesToAuthorities());
	}

}
