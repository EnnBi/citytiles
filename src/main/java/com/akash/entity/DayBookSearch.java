package com.akash.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class DayBookSearch {

	Long user;
	
	String transactionBy;
	
	String transactionNumber;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate endDate;
	
	String accountNumber;
	
	String status;
	
	String transactionType;

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getTransactionBy() {
		return transactionBy;
	}

	public void setTransactionBy(String transactionBy) {
		this.transactionBy = transactionBy;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
}
