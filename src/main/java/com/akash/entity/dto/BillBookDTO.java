package com.akash.entity.dto;

import java.time.LocalDate;

import com.akash.entity.Vehicle;

public class BillBookDTO {

	long id;
	
	String receiptNumber;
	
	String customerName;
	
	LocalDate date;
	
	String vehicle;
	
	String site;
	
	Double total;

	public BillBookDTO(long id, String receiptNumber, String customerName,String address, LocalDate date, 
			//Vehicle vehicle,
				String site,
			Double total) {
		super();
		this.id = id;
		this.receiptNumber = receiptNumber;
		this.customerName = customerName+"-"+address;
		this.date = date;
		//this.vehicle = vehicle != null?vehicle.getNumber():null;
		this.site = site;
		this.total = total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {   
		this.id = id;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
}
