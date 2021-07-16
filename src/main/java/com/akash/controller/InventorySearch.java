package com.akash.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class InventorySearch {

	Long product;
	
	Long size;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	LocalDate startDate;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	LocalDate endDate;

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		if(startDate ==null)
			this.startDate=LocalDate.of(2000, 1, 1);
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		if(endDate == null)
			this.endDate=LocalDate.of(2050, 12, 31);
	}	
}
