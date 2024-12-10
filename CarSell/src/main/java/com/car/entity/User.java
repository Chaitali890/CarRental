package com.car.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "cust_name")
	private String customerName;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "pincode")
	private Integer pincode;
	
	@Column(name = "contact")
	private Long contactNumber;
	
	@Column(name = "isDeleted",columnDefinition = "boolean default 0" )
	private Boolean isDeleted = false;
	
	@Column(name = "custIdentificationNumber")
	private String custIdentificationNumber;

}
