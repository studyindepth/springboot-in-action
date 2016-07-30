package io.github.toandv.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.toandv.domain.user.Account;
import io.github.toandv.domain.user.AccountRepository;

@Service
public class AccountDetailsService implements UserDetailsService {

	@Autowired
	AccountRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = repo.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return new User(account.getUsername(), account.getPassword(), getGrantedAuthorities(account.getUsername()));
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(String username) {
		if (username.equals("admin")) {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
}
