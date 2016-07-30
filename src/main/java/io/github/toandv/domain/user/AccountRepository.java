package io.github.toandv.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByUsername(String username);
}
