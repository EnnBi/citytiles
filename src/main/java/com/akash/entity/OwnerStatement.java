package com.akash.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OwnerStatement {

	String user;

	String transactionNumber;

	String transactionType;

	String transactionBy;

	String accountNumber;

	String responsiblePerson;

	Double amount;

	LocalDate date;

	Double debit;

	Double credit;

	Double balance;

	public OwnerStatement(LocalDate date, String user, String transactionNumber, String transactionType,
			String transactionBy, String accountNumber, String responsiblePerson, Double amount) {
		super();
		this.date = date;
		this.user = user;
		this.transactionNumber = transactionNumber;
		this.transactionType = transactionType;
		this.transactionBy = transactionBy;
		this.accountNumber = accountNumber;
		this.responsiblePerson = responsiblePerson;
		this.amount = amount;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionBy() {
		return transactionBy;
	}

	public void setTransactionBy(String transactionBy) {
		this.transactionBy = transactionBy;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	
}
