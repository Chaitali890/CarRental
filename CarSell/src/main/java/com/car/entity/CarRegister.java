package com.car.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="carDb")
@Data
public class CarRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "make")
	private String make;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "available")
	private Boolean available;
	
	@Column(name = "carUniqueNumber")
   private String identificationNumber;
	
	@ElementCollection
	@Column(name = "type")
	private List<String> type;
	
	@Column(name = "transmission")
	private String transmission;
	
	@ElementCollection
	@Column(name = "color")
	private List<String> color;
	
	@Column(name = "year")
	private String modelYear;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "description")
	private String description;
	
}
