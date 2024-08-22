package com.paytonkawa.commande_service.dto;

import java.util.ArrayList;
import java.util.List;

import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.entity.Product;

public class CommandProducts {
	
	private Command command;
	private List<Product> products = new ArrayList<>();
	
	public CommandProducts() {
		
	}
	
	public CommandProducts(Command command, List<Product> products) {
		super();
		this.command = command;
		this.products = products;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "CommandProducts [command=" + command + ", products=" + products + "]";
	}
	
	
	
	

}
