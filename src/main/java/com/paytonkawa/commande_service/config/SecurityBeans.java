package com.paytonkawa.commande_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.paytonkawa.commande_service.filter.SecurityFilter;
import com.paytonkawa.commande_service.rest_client.AuthenticationFeignClient;

@Configuration
public class SecurityBeans {

	@Autowired
	private AuthenticationFeignClient authenticationfeignclient;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
                .csrf().disable() // Disable CSRF protection for stateless authentication
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                .and()
                .addFilterBefore(new SecurityFilter(authenticationfeignclient), UsernamePasswordAuthenticationFilter.class) // Custom security filter
                .authorizeHttpRequests()
                    .anyRequest().authenticated() // All requests require authentication
                .and()
                .build(); // Build the security filter chain
    }
	
	
}

