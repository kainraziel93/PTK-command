package com.paytonkawa.commande_service.dto;

public class CommandDto {

	private int productId;
	private int quantity;
	
	public CommandDto() {

	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CommandDto [productId=" + productId + ", quantity=" + quantity + "]";
	}
	
	
}
