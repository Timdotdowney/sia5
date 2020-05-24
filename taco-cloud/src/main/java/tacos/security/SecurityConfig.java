package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/design", "/orders/**").access("hasRole('ROLE_USER')")
				.antMatchers("/", "/**").access("permitAll")
			.and()
				.authorizeRequests().antMatchers("/h2-console").permitAll()
			.and().formLogin().loginPage("/login")
			.and()
			.logout()
			.logoutSuccessUrl("/");
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}
