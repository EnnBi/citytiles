package com.akash.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill_book")
public class BillBook {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	AppUser customer;
	@Column(name = "address")
	private String address;
	@ManyToOne
	@JoinColumn(name = "site")
	Site site;
	@ManyToOne
	@JoinColumn(name = "vehicle")
	Vehicle vehicle;
	@Column(name = "reciept_number")
	private String recieptNumber;
	@Column(name = "unit")
	private double unit;
	@Column(name = "amount")
	private double amount;
	@Column(name = "loading_amount")
	private double loadingAmount;
	@Column(name = "unloading_amount")
	private double unloadingAmount;
	@Column(name = "total")
	private double total;
	@Column(name = "paid")
	private double paid;
	@Column(name = "balance")
	private double balance;
	@ManyToOne
	@JoinColumn(name = "sale")
	Sales sales;
	@ManyToOne
	@JoinColumn(name = "loaders")
	AppUser loaders;
	@ManyToOne
	@JoinColumn(name = "unloaders")
	AppUser unloaders;
	@Column(name = "carriage")
	private double carriage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AppUser getCustomer() {
		return customer;
	}

	public void setCustomer(AppUser customer) {
		this.customer = customer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getRecieptNumber() {
		return recieptNumber;
	}

	public void setRecieptNumber(String recieptNumber) {
		this.recieptNumber = recieptNumber;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getLoadingAmount() {
		return loadingAmount;
	}

	public void setLoadingAmount(double loadingAmount) {
		this.loadingAmount = loadingAmount;
	}

	public double getUnloadingAmount() {
		return unloadingAmount;
	}

	public void setUnloadingAmount(double unloadingAmount) {
		this.unloadingAmount = unloadingAmount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public AppUser getLoaders() {
		return loaders;
	}

	public void setLoaders(AppUser loaders) {
		this.loaders = loaders;
	}

	public AppUser getUnloaders() {
		return unloaders;
	}

	public void setUnloaders(AppUser unloaders) {
		this.unloaders = unloaders;
	}

	public double getCarriage() {
		return carriage;
	}

	public void setCarriage(double carriage) {
		this.carriage = carriage;
	}

	@Override
	public String toString() {
		return "BillBook [id=" + id + ", customer=" + customer + ", address=" + address + ", site=" + site
				+ ", vehicle=" + vehicle + ", recieptNumber=" + recieptNumber + ", unit=" + unit + ", amount=" + amount
				+ ", loadingAmount=" + loadingAmount + ", unloadingAmount=" + unloadingAmount + ", total=" + total
				+ ", paid=" + paid + ", balance=" + balance + ", sales=" + sales + ", loaders=" + loaders
				+ ", unloaders=" + unloaders + ", carriage=" + carriage + "]";
	}

}
