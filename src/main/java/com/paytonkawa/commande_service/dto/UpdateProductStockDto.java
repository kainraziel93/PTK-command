package com.paytonkawa.commande_service.dto;

public class UpdateProductStockDto {
private int productId;
private int quantity;


public UpdateProductStockDto(int productId, int quantity) {
	super();
	this.productId = productId;
	this.quantity = quantity;
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


	
}
