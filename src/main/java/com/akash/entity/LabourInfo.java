package com.akash.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="labour_info")
public class LabourInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="quantity")
	private Double quantity;
	
	@Column(name="amount_per_head")
	private Double amountPerHead;
	
	@Column(name="total_amount")
	private Double totalAmount;
	
	@NotNull(message = "Please select labours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(name = "Labourinfo_Labours")
	 List<AppUser> labours;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmountPerHead() {
		return amountPerHead;
	}

	public void setAmountPerHead(Double amountPerHead) {
		this.amountPerHead = amountPerHead;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<AppUser> getLabours() {
		return labours;
	}

	public void setLabours(List<AppUser> labours) {
		this.labours = labours;
	}

	@Override
	public String toString() {
		return "LabourInfo [id=" + id + ", quantity=" + quantity + ", amountPerHead=" + amountPerHead + ", totalAmount="
				+ totalAmount + ", labours=" + labours + "]";
	}
	
	

}
