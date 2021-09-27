package com.akash.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class LabourCost {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column(name="Rate")
	Double rate;
	
	@ManyToOne
	@JoinColumn(name = "Labour")
	AppUser labour;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "Date")
	private LocalDate date;

	public LabourCost() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}


	public AppUser getLabour() {
		return labour;
	}

	public void setLabour(AppUser labour) {
		this.labour = labour;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
}
