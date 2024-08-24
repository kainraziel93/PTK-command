package com.paytonkawa.commande_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("command")
public class CommandController {

	@Autowired
	private CommandServices commandServices;
	
	@Operation(summary = "get all commands", description = "get all commands available in the database")
	@GetMapping()
	public ResponseEntity<List<Command>> getAllCommands(){
		return this.commandServices.getAllCommands();
	}
	
	@Operation(summary = "get command by id", description = "get a command with the provided id if it exist in the database")
	@GetMapping("{id}")
	public  ResponseEntity<Command> commandById(@PathVariable("id") int commandId) {
		return this.commandServices.getCommandById(commandId);
	}
	@Operation(summary = "get products in a command", description = "get all products in a a command")
	@GetMapping("{id}/product")
	public ResponseEntity<List<Product>> getProductCommands(@PathVariable("id") int commandId){
		return this.commandServices.getCommandProducts(commandId);
	}
	@Operation(summary = "create a new command", description = "create a new command need to provide a valide product id and a quantity")
	@PostMapping()
	public ResponseEntity<Command> newCommand(@RequestBody CommandDto newCommand){
		
		return this.commandServices.addCommand(newCommand);
	}
	
	@Operation(summary = "add a product to an existing command", description = "the command should still be not validated in case it is you can not add a product to it")
	@PostMapping("add_to_cart/{commandId}")
	public ResponseEntity<Command> addProductToCart(@PathVariable("commandId") int commandId,@RequestBody CommandDto command){
		return this.commandServices.addProductToCart(commandId,command);
	}
	
	@Operation(summary = "delete  a command ", description = "delete an existing command need to provide a command id")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCommand(@PathVariable("id") int commandId){
		return this .commandServices.deleteCommand(commandId);
	}
	@Operation(summary = "validate a command", description = "validate a command ")
	@GetMapping("validate/{id}")
	public ResponseEntity<Map<String, String>> validateCommand(@PathVariable("id") int commandId){
		return this.commandServices.validateCommand(commandId);
	}

}
