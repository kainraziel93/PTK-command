package com.paytonkawa.commande_service.dto;


public class CustomerResponse {

	private int id;
	private String email;
	private String role;
	
	
	public CustomerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CustomerResponse(int id, String email, String role) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
	}
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "CustomerResponse [id=" + id + ", email=" + email + ", role=" + role + "]";
	}
	
	
	
}
