package com.akash.entity;

import java.time.LocalDate;
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
@Table(name="manufacture")
public class Manufacture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	Product product;
	
	@ManyToOne
	@JoinColumn(name="size")
	Size size;
	 @Column(name="date")
	LocalDate date;
	
	 @Column(name="total_quantity")
	private double totalQuantity;
	@Column(name="total_amount")
	private double totalAmount;
	@OneToMany
	@JoinColumn(name="labour_id")
	List<LabourInfo> labourInfo;
	
	@Column(name="cement")
	private int cement;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<LabourInfo> getLabourInfo() {
		return labourInfo;
	}

	public void setLabourInfo(List<LabourInfo> labourInfo) {
		this.labourInfo = labourInfo;
	}

	public int getCement() {
		return cement;
	}

	public void setCement(int cement) {
		this.cement = cement;
	}

	@Override
	public String toString() {
		return "Manufacture [id=" + id + ", product=" + product + ", size=" + size + ", date=" + date
				+ ", totalQuantity=" + totalQuantity + ", totalAmount=" + totalAmount + ", labourInfo=" + labourInfo
				+ ", cement=" + cement + "]";
	}
	
	

}
