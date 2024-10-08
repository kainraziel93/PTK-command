package com.paytonkawa.commande_service.rest_client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.paytonkawa.commande_service.entity.Product;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductFeignClient {
	@GetMapping()
	ResponseEntity<List<Product>> getAllProducts();
	@GetMapping("{id}")
	ResponseEntity<Product> getProductById(@PathVariable("id") int productId);
}
