package com.akash.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class RawMaterialSearch {
	
	
	private Long materialTypeId;
	
	private Long appUserId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate endDate;

	public Long getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(Long materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public Long getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
	
	

}
