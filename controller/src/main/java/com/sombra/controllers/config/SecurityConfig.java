package com.sombra.controllers.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {

//		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");

		final String findUserQuery = "select email, password, is_enable "
				+ "from users " + "where email = ?";
		final String findRoles =
                "SELECT u.email, ur.name from users u JOIN user_roles ur ON u.role_id = ur.role_id " +
                        "WHERE u.email = ?";

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(findUserQuery)
				.authoritiesByUsernameQuery(findRoles);


	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
				.and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests()
				.antMatchers("/edit/**").access("hasAuthority('ADMIN')")
				.and().exceptionHandling().accessDeniedPage("/403");

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/**").permitAll()
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("j_email")
                .passwordParameter("j_password")
                .permitAll();

        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }

}
