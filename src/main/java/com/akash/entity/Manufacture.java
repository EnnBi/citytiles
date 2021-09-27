package com.akash.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "manufacture")
public class Manufacture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "product")
	Product product;

	@ManyToOne
	@JoinColumn(name = "size")
	Size size;
	
	@ManyToOne
	@JoinColumn(name="color")
	Color color;

	@Column(name = "date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate date;

	@Column(name = "total_quantity")
	private Double totalQuantity;

	@Column(name = "total_amount")
	private Double totalAmount;

	@Column(name = "Cost_Per_Unit")
	private Double cpu;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "labour_id")
	List<LabourInfo> labourInfo;

	@Column(name = "cement")
	private Double cement;

	@ManyToOne
	@JoinColumn(name="Labour_Group")
	LabourGroup labourGroup;
	
	@ManyToMany(cascade = CascadeType.ALL)
	List<AppUser> fullDay;
	
	@ManyToMany(cascade = CascadeType.ALL)
	List<AppUser> halfDay;
	
	@Column(name = "quantity")
	private Double quantity;
	
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

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<LabourInfo> getLabourInfo() {
		return labourInfo;
	}

	public void setLabourInfo(List<LabourInfo> labourInfo) {
		this.labourInfo = labourInfo;
	}

	public Double getCement() {
		return cement;
	}

	public void setCement(Double cement) {
		this.cement = cement;
	}

	public Double getCpu() {
		return cpu;
	}

	public void setCpu(Double cpu) {
		this.cpu = cpu;
	}

	public LabourGroup getLabourGroup() {
		return labourGroup;
	}

	public void setLabourGroup(LabourGroup labourGroup) {
		this.labourGroup = labourGroup;
	}
	
	public List<AppUser> getFullDay() {
		return fullDay;
	}

	public void setFullDay(List<AppUser> fullDay) {
		this.fullDay = fullDay;
	}

	public List<AppUser> getHalfDay() {
		return halfDay;
	}

	public void setHalfDay(List<AppUser> halfDay) {
		this.halfDay = halfDay;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Manufacture [id=" + id + ", product=" + product + ", size=" + size + ", date=" + date
				+ ", totalQuantity=" + totalQuantity + ", totalAmount=" + totalAmount + ", labourInfo=" + labourInfo
				+ ", cement=" + cement + "]";
	}

}
