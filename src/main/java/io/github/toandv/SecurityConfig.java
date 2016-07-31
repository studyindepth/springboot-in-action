package io.github.toandv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.toandv.service.MyUserDetailsService;
import io.github.toandv.service.StatelessAuthenticationFilter;
import io.github.toandv.service.TokenAuthenticationService;

@Configuration
@EnableWebSecurity(debug = true)
// @EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MyUserDetailsService userDetailsService;

	// TODO - remove unnecessary filters
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/v1/api/auth/token").permitAll()
				.anyRequest().fullyAuthenticated()
				.and()
				.httpBasic().disable()
				.csrf().disable()
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService()), UsernamePasswordAuthenticationFilter.class);
				//.cors().
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	// @Bean
	public RoleHierarchyImpl roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
		return roleHierarchy;
	}

	@Bean
	public TokenAuthenticationService tokenAuthenticationService() {
		return new TokenAuthenticationService("secret", userDetailsService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

}
