package com.car.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "buying_car")
public class CarBuy {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer makeId;
	
	private Integer modelId;
	
	@Column(name = "transmission")
	private String transmission;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "carUniqueNumber")
	private String identificationNumber;
	
	@Column(name = "isDeleted", columnDefinition = "boolean default 0")
	private Boolean isDeleted;
}
