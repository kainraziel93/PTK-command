package com.paytonkawa.commande_service.rest_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paytonkawa.commande_service.dto.CustomerResponse;

@FeignClient(name="authentication-service",url="${authentication.service.url}")
public interface AuthenticationFeignClient {

	@GetMapping("verify_token")
	public ResponseEntity<CustomerResponse> getCustomerFromToken(@RequestParam(name="token") String token);
}
