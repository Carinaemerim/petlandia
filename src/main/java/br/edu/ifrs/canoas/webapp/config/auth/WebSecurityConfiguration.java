package br.edu.ifrs.canoas.webapp.config.auth;

import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@AllArgsConstructor
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    UserDetailsImplService accountUserDetailsService;

	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/img/**", "/css/**", "/js/**", "/db/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/announces/**", "/logout").permitAll()
				.antMatchers("/login", "/user/create").anonymous()
				.antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login").permitAll()
            .and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .and()
        .csrf()
            .disable();
	}
}