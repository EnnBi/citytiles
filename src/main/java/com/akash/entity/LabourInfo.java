package com.akash.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="labour_info")
public class LabourInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="quantity")
	private double quantity;
	@Column(name="amount_per_head")
	private double amountPerHead;
	@Column(name="total_amount")
	private double totalAmount;
	@Column(name="labours")
	private String labours;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmountPerHead() {
		return amountPerHead;
	}

	public void setAmountPerHead(double amountPerHead) {
		this.amountPerHead = amountPerHead;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getLabours() {
		return labours;
	}

	public void setLabours(String labours) {
		this.labours = labours;
	}

	@Override
	public String toString() {
		return "LabourInfo [id=" + id + ", quantity=" + quantity + ", amountPerHead=" + amountPerHead + ", totalAmount="
				+ totalAmount + ", labours=" + labours + "]";
	}
	
	

}
