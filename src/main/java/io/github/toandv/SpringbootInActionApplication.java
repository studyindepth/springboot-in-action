package io.github.toandv;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import io.github.toandv.domain.Book;
import io.github.toandv.domain.BookRepository;
import io.github.toandv.domain.user.Role;
import io.github.toandv.domain.user.RoleRepository;
import io.github.toandv.domain.user.User;
import io.github.toandv.domain.user.UserRepository;

@SpringBootApplication
public class SpringbootInActionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootInActionApplication.class, args);
	}

	@Bean
	CommandLineRunner init(final BookRepository bookRepository, DataSource dataSource, JdbcTemplate jdbcTemplate,
			PlatformTransactionManager tm, EntityManager em, UserRepository accountRepository,
			RoleRepository roleRepo, ApplicationContext context) {
		return new CommandLineRunner() {
			@Override
			public void run(String... arg0) throws Exception {
				List<Book> books = Arrays.asList(
						new Book("toan", "sbia", "Spring Boot In Action", "Craig Walls", "How to use boot"),
						new Book("toan", "nia", "Netty In Action", "Norman Maurer", "Mastering Netty"));
				bookRepository.save(books);
				accountRepository.save(new User("admin", "admin", Arrays.asList(new Role(Role.RoleName.ROLE_ADMIN))));
				accountRepository.save(new User("user", "user", Arrays.asList(new Role(Role.RoleName.ROLE_USER))));
				String[] beanNames = context.getBeanDefinitionNames();
				for (String bean : beanNames) {
					System.out.println(bean);
				}
			}
		};

	}
}
