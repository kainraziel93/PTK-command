package com.paytonkawa.commande_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.entity.Product;
import com.paytonkawa.commande_service.rest_client.ProductFeignClient;
import com.paytonkawa.commande_service.services.CommandServices;

@RestController
@RequestMapping("command")
public class CommandController {

	@Autowired
	private CommandServices commandServices;
	@GetMapping()
	public ResponseEntity<Command> products(){

		return this.commandServices.addProductToCommand(1, 1, 2);
	}
}
