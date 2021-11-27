package com.quick.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableWebSecurity()
public class Application extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.csrf().disable()
				.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/**")
				.hasRole("APIUSER").and().httpBasic().and()
				.headers().frameOptions().sameOrigin();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("api")
				.roles("APIUSER")
				.password("{noop}123");
	}

}
