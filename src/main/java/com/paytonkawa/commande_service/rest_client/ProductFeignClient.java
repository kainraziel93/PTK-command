package com.paytonkawa.commande_service.rest_client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.paytonkawa.commande_service.entity.Product;

@FeignClient(name = "product-service", url = "http://localhost:8080/product")
public interface ProductFeignClient {
	@GetMapping()
	List<Product> getAllProducts();
}
