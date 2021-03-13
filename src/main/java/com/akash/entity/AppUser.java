package com.akash.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="app_user")
public class AppUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	@Column(name="contact")
	private String contact;
	@Column(name="address")
	private String address;
	@ManyToOne
	@JoinColumn(name="user_type_id")
	UserType userType;
	
	@Column(name="ledger_number")
	private String ledgerNumber;
	@Column(name="account_number")
	private String accountNumber;
	@OneToMany
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
