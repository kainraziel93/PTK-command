package com.paytonkawa.commande_service.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paytonkawa.commande_service.dto.CustomerResponse;
import com.paytonkawa.commande_service.rest_client.AuthenticationFeignClient;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter extends OncePerRequestFilter {

	private AuthenticationFeignClient authenticationFeignClient;
	
	
	public SecurityFilter(AuthenticationFeignClient authenticationFeignClient) {
		super();
		this.authenticationFeignClient = authenticationFeignClient;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Authentication authentication;
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(header!=null && header.toLowerCase().contains("bearer")) {
			System.out.println("header authorization found");
			String token = header.split(" ")[1];
			System.out.println("token=>"+token);
			ResponseEntity<CustomerResponse> customerResponse = authenticationFeignClient.getCustomerFromToken(token);
			if(customerResponse.getStatusCode().is2xxSuccessful()) {
				System.out.println("CustomerResponse is ok=>" +customerResponse.getBody());
				CustomerResponse customer  = customerResponse.getBody();
				authentication = new UsernamePasswordAuthenticationToken(customer.getEmail(),null,List.of(new SimpleGrantedAuthority(customer.getRole())));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				request.setAttribute("customer-id", customer.getId());
				System.out.println("authenticated succefully");
			}
		}
		filterChain.doFilter(request, response);

		
	}

}
