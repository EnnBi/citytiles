package com.akash.entity.dto;

import java.time.LocalDate;

public class ManufactureDTO {

	long id;
	
	String product;
	
	String size;
	
	LocalDate date;
	
	Double quantity;
	
	Double amount;
	
	
	public ManufactureDTO(long id, String product, String size, LocalDate date, Double quantity, Double amount) {
		super();
		this.id = id;
		this.product = product;
		this.size = size;
		this.date = date;
		this.quantity = quantity;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
