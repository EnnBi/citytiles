package com.akash.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@NotNull(message ="number is required")
	@NotEmpty(message="number is required")
	@Column(name="number")
	private String number;
	
	@ManyToOne
	@JoinColumn(name="appUser_id")
	@NotNull(message="select any driver")
	AppUser driver;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public AppUser getDriver() {
		return driver;
	}

	public void setDriver(AppUser driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", number=" + number + ", driver=" + driver + "]";
	}
	
	

}
