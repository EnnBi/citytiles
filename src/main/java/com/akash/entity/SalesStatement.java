package com.akash.entity;

public class SalesStatement {

	String product;
	
	Double quantity;
	
	Double unitPrice;
	
	Double amount;
	
	

	public SalesStatement(String product,String size, Double quantity, Double unitPrice, Double amount) {
		super();
		this.product = product+"-"+size;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.amount = amount;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return product+"   "+quantity+"  "+unitPrice+"  "+amount+" /n";
	}
	
}
