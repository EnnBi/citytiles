package com.akash.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class StatementSearch {

	String userType;
	
	Long user;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	LocalDate startDate;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	LocalDate endDate;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
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
