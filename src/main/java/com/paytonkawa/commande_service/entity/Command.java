package com.paytonkawa.commande_service.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Command {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="customer id cannot be blank")
	private int customerId;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> products;
	private LocalDateTime createdAt;
	
	
	public Command() {
	}


	public Command(@NotBlank(message = "customer id cannot be blank") int customerId,
			LocalDateTime createdAt) {
		super();
		this.customerId = customerId;
		this.products = new ArrayList<>();
		this.createdAt = createdAt;
	}
	
	
	public void addProductToCommand(Product product) {
		if(this.products==null) this.products = new ArrayList<>();
		this.products.add(product);
	}

	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public List<Product> getProducts() {
		return products;
	}




	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		return "Command [id=" + id + ", customerId=" + customerId + ", products=" + products + ", createdAt="
				+ createdAt + "]";
	}
	
	
	
	
	
	
	
}
