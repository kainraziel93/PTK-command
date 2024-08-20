package com.paytonkawa.commande_service.rest_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "customer-service", url = "http://localhost:8080/customer")
public class CsutomerFeignClient {


	
}
