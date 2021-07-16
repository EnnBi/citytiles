package com.akash.entity;

public class InventoryCount {

	String name;
	
	String size;
	
	Double manufactured;
	
	Double sold;
	
	Double remaining;

	
	
	public InventoryCount(String name, String size, Double manufactured, Double sold) {
		super();
		this.name = name;
		this.size = size;
		this.manufactured = manufactured==null?Double.valueOf(0):manufactured;
		this.sold = sold == null?Double.valueOf(0):sold;
		this.remaining = this.manufactured-this.sold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getManufactured() {
		return manufactured;
	}

	public void setManufactured(Double manufactured) {
		this.manufactured = manufactured;
	}

	public Double getSold() {
		return sold;
	}

	public void setSold(Double sold) {
		this.sold = sold;
	}

	public Double getRemaining() {
		return remaining;
	}

	public void setRemaining(Double remaining) {
		this.remaining = remaining;
	}

	
}
