package com.car.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RentalCar")
@Data
public class RentalCar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "carId")
	private String carId;
	
	@Column(name = "custId")
	private String custId;
	
	@Column(name = "custName")
	private String custName;
	
	@Column(name = "rentalFees")
	private Integer rentalFees;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "dueDate")
	private LocalDate dueDate;
	
	@Column(name = "isDeleted",columnDefinition = "boolean default 0" )
	private Boolean isDeleted = false;
	
	private Integer daysElapsed;
	
	private Long fine;
}
