package com.paytonkawa.commande_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytonkawa.commande_service.dto.CommandDto;
import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.entity.Product;
import com.paytonkawa.commande_service.services.CommandServices;

@RestController
@RequestMapping("command")
public class CommandController {

	@Autowired
	private CommandServices commandServices;
	
	@PostMapping()
	public ResponseEntity<Command> newCommand(@RequestBody CommandDto newCommand){
		
		return this.commandServices.addCommand(newCommand);
	}
	
	@PostMapping("add_to_cart/{commandId}")
	public ResponseEntity<Command> addProductToCart(@PathVariable("commandId") int commandId,@RequestBody CommandDto command){
		return this.commandServices.addProductToCart(commandId,command);
	}
	
	@GetMapping("product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id){

		
		Product p = this.commandServices.getProductById(id);
		return ResponseEntity.ok(p);
	}
}
