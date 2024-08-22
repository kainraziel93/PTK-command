package com.paytonkawa.commande_service.rest_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.paytonkawa.commande_service.entity.Customer;

@FeignClient(name = "customer-service", url = "${customer-service.url}")
public interface CsutomerFeignClient {
	@GetMapping("{id}")
	public ResponseEntity<Customer>getClientById(@PathVariable("id") int id);
	
}
