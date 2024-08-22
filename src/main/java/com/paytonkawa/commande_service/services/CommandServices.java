package com.paytonkawa.commande_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.entity.Product;
import com.paytonkawa.commande_service.repo.CommandRepo;
import com.paytonkawa.commande_service.rest_client.ProductFeignClient;

@Service
public class CommandServices {

	@Autowired
	private CommandRepo commandRepo;
	@Autowired
	private ProductFeignClient productRestClient;
	public ResponseEntity<List<Command>> getAllCommands() {
		List<Command> commands = this.commandRepo.findAll();
		return ResponseEntity.ok(commands);
	}
	
	public ResponseEntity<Command> addProductToCommand(int commandId,int productId,int quantity){
		Product product = getProductById(productId);
		if(product!=null && product.getStock()>=quantity) {
			try {
				Command command = this.commandRepo.findById(commandId).get();
				product.setStock(product.getStock()-product.getQuantity());
				command.getProducts().add(product);
				//update the product database by comunicating with product-service with kafka(to add later)
				return ResponseEntity.ok(command);
			} catch (Exception e) {
			return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	public Product getProductById(int productId){
		ResponseEntity<Product> productResponse = this.productRestClient.getProductById(productId);
		if(productResponse.getStatusCode().is2xxSuccessful()){
			Product product = productResponse.getBody();
			return product;
		}
		return null;
	}
}
