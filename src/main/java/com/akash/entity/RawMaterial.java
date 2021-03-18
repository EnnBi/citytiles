package com.akash.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="raw_material")
public class RawMaterial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@NotNull(message="select any material")
	@ManyToOne
	@JoinColumn(name="material_id")
	MaterialType material;
	
	@NotNull(message="select any app user")
	@ManyToOne
	@JoinColumn(name="dealer")
	AppUser dealer;
	
	@Column(name="date")
	LocalDate date;
	
	@NotEmpty(message="chalan number is required")
	@NotNull(message ="chalan number is required")
	@Column(name="chalan_number")
	private String chalanNumber;
	
	//@NotEmpty(message="quantity is required")
	@NotNull(message ="quantity is required")
	@Column(name="quantity")
	private Double quantity;
	
	//@NotEmpty(message="amount is required")
	@NotNull(message ="amount is required")
	@Column(name="amount")
	private Double amount;
	
	@NotEmpty(message="unit is required")
	@NotNull(message ="unit is required")
	@Column(name="unit")
	private String unit;
	
	public RawMaterial()
	{
	 amount=0.0;
	 quantity=0.0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MaterialType getMaterial() {
		return material;
	}

	public void setMaterial(MaterialType material) {
		this.material = material;
	}

	public AppUser getDealer() {
		return dealer;
	}

	public void setDealer(AppUser dealer) {
		this.dealer = dealer;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getChalanNumber() {
		return chalanNumber;
	}

	public void setChalanNumber(String chalanNumber) {
		this.chalanNumber = chalanNumber;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "RawMaterial [id=" + id + ", material=" + material + ", dealer=" + dealer + ", date=" + date
				+ ", chalanNumber=" + chalanNumber + ", quantity=" + quantity + ", amount=" + amount + ", unit=" + unit
				+ "]";
	}
	
	
	
	

}
