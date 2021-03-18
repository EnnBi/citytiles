package com.akash.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Entity
@Table(name="app_user")
public class AppUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@NotNull(message ="name is required")
	@NotEmpty(message="name is required")
	@Column(name="name")
	private String name;
	
	@Pattern(regexp = "^\\d{10}$", message = "Please Enter the 10 digits correctly")
	@NotNull(message ="contact is required")
	@NotEmpty(message="contact is required")
	@Column(name="contact")
	private String contact;
	
	
	@NotNull(message ="address is required")
	@NotEmpty(message="address is required")
	@Column(name="address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name="user_type_id")
	@NotNull(message ="select any userType")
	UserType userType;
	
	@NotNull(message ="Ledger Number is required")
	@NotEmpty(message="Ledger Number is required")
	@Column(name="ledger_number")
	private String ledgerNumber;
	
	@NotNull(message ="Account Number is required")
	@NotEmpty(message="Account Number is required")
	@Column(name="account_number")
	private String accountNumber;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="site")
	List<Site> sites;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getLedgerNumber() {
		return ledgerNumber;
	}
	public void setLedgerNumber(String ledgerNumber) {
		this.ledgerNumber = ledgerNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public List<Site> getSites() {
		return sites;
	}
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	@Override
	public String toString() {
		return "AppUser [id=" + id + ", name=" + name + ", contact=" + contact + ", address=" + address + ", userType="
				+ userType + ", ledgerNumber=" + ledgerNumber + ", accountNumber=" + accountNumber + ", sites=" + sites
				+ "]";
	}
	
	
	

}
